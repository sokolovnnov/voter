package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.repo.jpa.JpaRestaurantRepo;

import java.time.LocalDate;
import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final JpaRestaurantRepo repository;

    //todo добавить проверку
    public RestaurantService(JpaRestaurantRepo repository) {
        this.repository = repository;
    }

    public Restaurant get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

     public List<Restaurant> getActiveByDate(LocalDate ld){
        return repository.getActiveByDate(ld);
    }

    public List<Restaurant> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant, userId), restaurant.id());
    }

    public Restaurant create(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant, userId);
    }

}
