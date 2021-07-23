package ru.antisida.voter.web;

import org.springframework.stereotype.Controller;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.service.MealService;
import ru.antisida.voter.service.MenuService;
import ru.antisida.voter.service.RestaurantService;
import ru.antisida.voter.to.Menu;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Controller
public class RestaurantController {


    private final RestaurantService service;
    private final MenuService menuService;


    public RestaurantController(RestaurantService service, MenuService menuService) {
        this.service = service;
        this.menuService = menuService;
    }

    public Restaurant get(int id) {
//        int userId = SecurityUtil.authUserId();
//        log.info("get meal {} for user {}", id, userId);
        return service.get(id, 1);//fixme userId
    }

    public void delete(int id) {
//        int userId = SecurityUtil.authUserId();
//        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, 1);//fixme userId
    }

//    @GetMapping("/restaurants")
    public List<Restaurant> getAll() {
//        int userId = SecurityUtil.authUserId();
//        log.info("getAll for user {}", userId);
//        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        return service.getAll(1);//fixme userId
    }

    public Restaurant create(Restaurant restaurant) {
//        int userId = SecurityUtil.authUserId();
//        checkNew(meal);
//        log.info("create {} for user {}", meal, userId);
        return service.create(restaurant, 1);//fixme userId
    }

    public void update(Restaurant restaurant, int id) {
//        int userId = SecurityUtil.authUserId();
//        assureIdConsistent(restaurant, id);
//        log.info("update {} for user {}", meal, userId);
        service.update(restaurant, 1);//fixme userId
    }

/*
    public HashSet<Menu> getMenusByDate(int userId, LocalDate ld){
        return menuService.getAllByDate(userId, ld);
    }
*/

//    public Menu getMenuByDateAndRestaurant(int userId, int restaurantId/*, LocalDate ld*/){//todo  c датой что то сдалать
//        return mealService.getMenuByDateAndRestaurant(userId, restaurantId);
//    }


}
