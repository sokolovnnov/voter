package ru.antisida.voter.repo.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.to.Menu;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepo {

    @PersistenceContext
    EntityManager em;

    public Meal get(int id, int userId){ //fixme тут не нужна проверка на юзера. для любых авторизированных юзеров
        // доступ есть
        return em.find(Meal.class, id);
    }

    public List<Meal> getAll(int userId){
        return em.createQuery("SELECT m FROM Meal m ORDER BY m.price ASC ", Meal.class).getResultList();//fixme
    }

    public List<Meal> getAllByDate(int userId, LocalDate ld){//todo проверить
        return em.createQuery("SELECT m FROM Meal m WHERE m.date =: ld", Meal.class)
                .setParameter("ld", ld)
                .getResultList();
    }

    public List<Meal> getAllByRestaurant(int userId, int restaurantId){
        return em.createQuery("SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId"
                , Meal.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

    @Transactional
    public Meal save(Meal meal, int userId){
//            meal.setCreatorId(userId);
            if (meal.isNew()) {
                em.persist(meal);
                return meal;
            } else if (get(meal.id(), userId) == null) {
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
