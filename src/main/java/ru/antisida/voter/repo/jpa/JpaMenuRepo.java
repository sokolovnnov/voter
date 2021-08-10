package ru.antisida.voter.repo.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.to.Menu;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JpaMenuRepo {     // fixme удалить класс!!!

    @PersistenceContext
    EntityManager em;

    public Menu get(int userId, int restaurantId, LocalDate ld){//todo c датой что-то сдeлать
        List<Meal> meals = em.createQuery("SELECT m FROM Meal m WHERE m.restaurant.id =:restaurantId AND m.date=:ld"
                , Meal.class)
                .setParameter("restaurantId", restaurantId)
                .setParameter("ld", ld)
                .getResultList();
//        Restaurant restaurant = em.getReference(Restaurant.class, restaurantId);
//        Restaurant restaurant = em.find(Restaurant.class, restaurantId);
        return new Menu(/*meals.get(0).getRestaurant(), */meals/*, ld*/);
    }

    public List<Menu> getAllByDate(int userId, LocalDate ld){
        Map<Restaurant, List<Meal>> meals = em.createQuery("SELECT m FROM Meal m WHERE m.date =: ld", Meal.class)
                .setParameter("ld", ld)
                .getResultList().stream()
                .collect(Collectors.groupingBy(meal -> meal.getRestaurant()))
                ;
        return meals.values()
                .stream()
                .map(mealList -> {
                    Menu menu = new Menu(mealList);
                    //menu.setRestaurant(mealList.get(0).getRestaurant());
                    return menu;
                })
                .collect(Collectors.toList())
                ;
    }

    public List<Menu> getAllByRestaurant(int userId, int restaurantsId){
        Map<LocalDate, List<Meal>> meals = em.createQuery("SELECT m FROM Meal m WHERE m.restaurant.id =: restaurantId",
                Meal.class)
                .setParameter("restaurantId", restaurantsId)
                .getResultList().stream()
                .collect(Collectors.groupingBy(meal -> meal.getDate()))
                ;
        return meals.values()
                .stream()
                .map(mealList -> {
                    Menu menu = new Menu(mealList);
                    menu.setRestaurant(em.getReference(Restaurant.class, restaurantsId));
                    return menu;
                })
                .collect(Collectors.toList())
                ;
    }
}
