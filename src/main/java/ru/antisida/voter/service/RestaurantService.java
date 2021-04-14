package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.repo.jpa.JpaRestaurantRepo;

import java.util.List;

@Service
public class RestaurantService {

private final JpaRestaurantRepo repository;
//todo добавить проверку
    public RestaurantService(JpaRestaurantRepo repository) {
        this.repository = repository;
    }

    public Restaurant get(int id, int userId) {
        return repository.get(id, userId);
        //return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        repository.delete(id, userId);
        //checkNotFoundWithId(repository.delete(id, userId), id);
    }

   /* public List<Meal> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
    }*/

    public List<Restaurant> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Restaurant restaurant, int userId) {
        repository.save(restaurant, userId);
//        Assert.notNull(meal, "meal must not be null");
//        checkNotFoundWithId(repository.save(meal, userId), meal.id());
    }

    public Restaurant create(Restaurant restaurant, int userId) {
       // Assert.notNull(restaurant, "meal must not be null");
        return repository.save(restaurant, userId);
    }

}
