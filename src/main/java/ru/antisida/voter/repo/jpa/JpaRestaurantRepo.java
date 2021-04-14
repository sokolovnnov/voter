package ru.antisida.voter.repo.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepo {

    @PersistenceContext
    private EntityManager em;

    public Restaurant get(int id, int userId){//fixme наверное, надо удалить userId/ Он тут не нужен
        return em.find(Restaurant.class, id);
    }

    public List<Restaurant> getAll(int userId){//fixme наверное, надо удалить userId/ Он тут не нужен
        return em.createQuery("SELECT r FROM Restaurant r", Restaurant.class).getResultList();
    }

    @Transactional
    public Restaurant save(Restaurant restaurant, int userId){//fixme добавить сюда проверку на АДМИНА
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            if (get(restaurant.getId(), userId) != null)//fixme добавить сюда проверку на АДМИНА
                return em.merge(restaurant);
            else return null;
        }
    }

    @Transactional
    public boolean delete(int id, int userId){
        return em.createQuery("DELETE FROM Restaurant r WHERE r.id=:id") //fixme добавить сюда проверку на АДМИНА
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

}