package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import ru.antisida.voter.model.User;
import ru.antisida.voter.repo.jpa.JpaUserRepo;

import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.*;

@Service
public class UserService {

    private JpaUserRepo repo;

    public User get(int id, int userId){
        return checkNotFoundWithId(repo.get(id, userId), id);
    }

    public List<User> getAll(int userId){
        return repo.getAll(userId);
    }

    public User save(User user, int userId){
        return repo.save(user, userId);
    }

    public void delete(int id, int userId){
        checkNotFoundWithId(repo.delete(id, userId), id);
    }

}
