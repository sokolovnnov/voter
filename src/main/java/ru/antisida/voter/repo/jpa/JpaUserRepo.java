package ru.antisida.voter.repo.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public class JpaUserRepo {

    @PersistenceContext
    EntityManager em;

    public User get(int id, int userId){
        return em.find(User.class, id);
    }

    public List<User> getAll(int userId){
        return em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class).getResultList();
    }

    public User save(User user, int userId){
        if(user.isNew()){
            em.persist(user);
            return user;
        } else if (get(user.id(), userId) == null){
            return null;
        }
        return em.merge(user);
    }

    public boolean delete(int id, int userId){
        return em.createQuery("DELETE FROM User u WHERE u.id =: id")
                .setParameter("id", id)
               .executeUpdate() != 0;
    }

}
