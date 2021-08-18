package ru.antisida.voter.repo.datajpa;

import org.springframework.stereotype.Repository;
import ru.antisida.voter.model.User;

import java.util.List;

@Repository
public class DataJpaUserRepo {

    private final CrudUserRepo crudUserRepo;

    public DataJpaUserRepo(CrudUserRepo crudUserRepo) {
        this.crudUserRepo = crudUserRepo;
    }

    public User save(User user) {
        return crudUserRepo.save(user);
    }

    public User get(int id){
        return crudUserRepo.findById(id).orElse(null);
    }

    public List<User> getAll(){
        return crudUserRepo.findAll();
    }

    public boolean delete(int id){
        return  crudUserRepo.delete(id) != 0;
    }

    public User getByEmail(String email) {
        return crudUserRepo.getByEmail(email);
    }

}
