package ru.antisida.voter.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.repo.datajpa.DataJpaMealRepo;
import ru.antisida.voter.repo.datajpa.DataJpaRestaurantRepo;
import ru.antisida.voter.service.mappers.RestaurantMapper;
import ru.antisida.voter.to.Menu;
import ru.antisida.voter.to.RestaurantTo;
import ru.antisida.voter.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.*;

@Service
public class RestaurantService {

    private final DataJpaRestaurantRepo repository;
    private final DataJpaMealRepo mealRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(DataJpaRestaurantRepo repository, DataJpaMealRepo mealRepository, RestaurantMapper restaurantMapper) {
        this.repository = repository;
        this.mealRepository = mealRepository;
        this.restaurantMapper = restaurantMapper;
    }

    public RestaurantTo get(int id) {
        return restaurantMapper.toTo(checkNotFoundWithId(repository.get(id), id));
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public List<RestaurantTo> getActiveByDate(LocalDate ld){
        return restaurantMapper.toTos(repository.getActiveByDate(ld));
    }

    @Cacheable("restaurant")
    public List<RestaurantTo> getAll() {
        return restaurantMapper.toTos(repository.getAll());
    }

    public void update(RestaurantTo restaurantTo, int id) {
        Assert.notNull(restaurantTo, "restaurant must not be null");
        assureIdConsistent(restaurantTo, id);
        Restaurant updated = checkNotFoundWithId(repository.get(restaurantTo.id()), restaurantTo.id());
        restaurantMapper.updateEntity(restaurantTo, updated);
        repository.save(updated);
    }

    public RestaurantTo create(RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        Assert.notNull(restaurantTo, "restaurant must not be null");
        Restaurant newRestaurant = repository.save(restaurantMapper.toEntity(restaurantTo));
        return restaurantMapper.toTo(newRestaurant);
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
