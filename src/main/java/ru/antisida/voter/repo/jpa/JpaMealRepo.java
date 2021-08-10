package ru.antisida.voter.repo.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Meal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepo {

    @PersistenceContext
    EntityManager em;

    public Meal get(int id){
        return em.find(Meal.class, id);
    }

    public List<Meal> getAll(int userId){
        return em.createQuery("SELECT m FROM Meal m ORDER BY m.price ASC ", Meal.class).getResultList();
    }

    public List<Meal> getAllByDate(int userId, LocalDate ld){//todo проверить
        return em.createQuery("SELECT m FROM Meal m WHERE m.date =: ld", Meal.class)
                .setParameter("ld", ld)
                .getResultList();
    }

    public List<Meal> getAllByRestaurant(int userId, int restaurantId){
        return em.createQuery("SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId", Meal.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

    @Transactional
    public Meal save(Meal meal){
            if (meal.isNew()) {
                em.persist(meal);
                return meal;
            } else if (get(meal.id()) == null) {
                return null;
            }
            return em.merge(meal);
        }

    @Transactional
    public boolean delete(int id, int userId){
        return em.createQuery("DELETE FROM Meal m WHERE m.id = :id")
                .setParameter("id", id)
        .executeUpdate() != 0;
    }
    
}
