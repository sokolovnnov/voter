package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import ru.antisida.voter.model.User;
import ru.antisida.voter.repo.datajpa.DataJpaUserRepo;

import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    private final DataJpaUserRepo repo;

    public UserService(DataJpaUserRepo repo) {
        this.repo = repo;
    }

    public User get(int id, int userId){
        return checkNotFoundWithId(repo.get(id), id);
    }

    public List<User> getAll(int userId){
        return repo.getAll();
    }

    public User save(User user, int userId){
        return repo.save(user);
    }

    public void delete(int id, int userId){
        checkNotFoundWithId(repo.delete(id), id);
    }

}
