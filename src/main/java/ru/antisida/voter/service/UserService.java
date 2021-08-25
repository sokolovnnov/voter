package ru.antisida.voter.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.antisida.voter.AuthorizedUser;
import ru.antisida.voter.model.User;
import ru.antisida.voter.repo.datajpa.DataJpaUserRepo;
import ru.antisida.voter.service.mappers.UserCreateToMapper;
import ru.antisida.voter.service.mappers.UserMapper;
import ru.antisida.voter.to.UserCreateTo;
import ru.antisida.voter.to.UserTo;
import ru.antisida.voter.util.ValidationUtil;

import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final DataJpaUserRepo repo;
    private final UserMapper userMapper;
    private final UserCreateToMapper userCreateToMapper;

    public UserService(DataJpaUserRepo repo, UserMapper userMapper, UserCreateToMapper userCreateToMapper) {
        this.repo = repo;
        this.userMapper = userMapper;
        this.userCreateToMapper = userCreateToMapper;
    }

    public UserTo get(int id) {
        return userMapper.toTo(checkNotFoundWithId(repo.get(id), id));
    }

    @Cacheable("user")
    public List<UserTo> getAll() {
        return userMapper.toTos(repo.getAll());
    }

    @CacheEvict(value = "user", allEntries = true)
    public UserTo create(UserCreateTo userCreateTo) {
        Assert.notNull(userCreateTo, "user must not be null");
        ValidationUtil.checkNew(userCreateTo);
        User newUser = repo.save(userCreateToMapper.toEntity(userCreateTo));
        return userMapper.toTo(newUser);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repo.delete(id), id);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional
    public void update(UserCreateTo userCreateTo, int id) {
        Assert.notNull(userCreateTo, "user must not be null");
        ValidationUtil.assureIdConsistent(userCreateTo, id);
        User user = checkNotFoundWithId(repo.get(userCreateTo.id()), id);
        userCreateToMapper.updateEntity(userCreateTo, user);
    }

    public UserTo getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return userMapper.toTo(ValidationUtil.checkNotFound(repo.getByEmail(email), "email=" + email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
