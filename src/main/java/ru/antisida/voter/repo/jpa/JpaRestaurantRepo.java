package ru.antisida.voter.repo.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        return em.createQuery("SELECT r FROM Restaurant r ORDER BY r.id ASC ", Restaurant.class).getResultList();
    }

    public List<Restaurant> getActiveByDate(LocalDate ld){
        LocalDateTime startDay = ld.atStartOfDay();
        LocalDateTime endDay = ld.plusDays(1).atStartOfDay();

        return em.createQuery("SELECT DISTINCT (r) FROM Restaurant r INNER JOIN r.meals m WHERE  m.date =: ld",
                Restaurant.class)
                .setParameter("ld", ld)
                .getResultList();
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
