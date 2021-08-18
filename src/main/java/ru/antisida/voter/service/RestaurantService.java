package ru.antisida.voter.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.repo.datajpa.DataJpaMealRepo;
import ru.antisida.voter.repo.datajpa.DataJpaRestaurantRepo;
import ru.antisida.voter.to.Menu;
import ru.antisida.voter.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final DataJpaRestaurantRepo repository;
    private final DataJpaMealRepo mealRepository;

    //todo добавить проверку
    public RestaurantService(DataJpaRestaurantRepo repository, DataJpaMealRepo mealRepository) {
        this.repository = repository;
        this.mealRepository = mealRepository;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

     public List<Restaurant> getActiveByDate(LocalDate ld){
        return repository.getActiveByDate(ld);
    }

    @Cacheable("restaurant")
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public void update(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    //fixme при создании меню каждый раз идет запрос к базе, чтобы вытянуть ресторан. Чтобы такого не было:
    //fixme надо вытягивать и еду и рестораны одним запросом и сопоставлять их в репозитории
    @Cacheable("dateMenu")
    public List<Menu> getAllMenuByDate(LocalDate ld){
        return MenuUtil.toDateListMenu(mealRepository.getAllByDate(ld));
    }

    @Cacheable("restaurantMenu")
    public List<Menu> getAllMenuByRestaurant(int restaurantId){
        return MenuUtil.toRestaurantListMenu(mealRepository.getAllByRestaurant(restaurantId));
    }

    @Cacheable("menu")
    public Menu getMenu(int restaurantId, LocalDate ld){
        return MenuUtil.toMenu(mealRepository.getAllByRestaurantAndByDate(restaurantId, ld));
    }

}
