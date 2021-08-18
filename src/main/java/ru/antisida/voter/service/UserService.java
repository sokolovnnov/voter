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
import ru.antisida.voter.to.UserTo;
import ru.antisida.voter.util.ValidationUtil;

import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final DataJpaUserRepo repo;

    public UserService(DataJpaUserRepo repo) {
        this.repo = repo;
    }

    @CacheEvict(value = "user", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repo.save(user);
    }

    public User get(int id){
        return checkNotFoundWithId(repo.get(id), id);
    }

    @Cacheable("user")
    public List<User> getAll(){
        return repo.getAll();
    }

    public User save(User user){
        return repo.save(user);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void delete(int id){
        checkNotFoundWithId(repo.delete(id), id);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repo.save(user);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        User updatedUser = UserTo.updateFromTo(user, userTo);
        repo.save(updatedUser);   // !! need only for JDBC implementation
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return ValidationUtil.checkNotFound(repo.getByEmail(email), "email=" + email);
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
