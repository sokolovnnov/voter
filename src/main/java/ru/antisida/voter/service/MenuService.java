package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.repo.jpa.JpaMenuRepo;
import ru.antisida.voter.repo.jpa.JpaRestaurantRepo;
import ru.antisida.voter.to.Menu;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final JpaMenuRepo menuRepository;

    public MenuService(JpaMenuRepo menuRepository, JpaRestaurantRepo restaurantRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAllByDate(int userId, LocalDate ld){
        return menuRepository.getAllByDate(userId, ld);
/*        // достаем все рестораны, у которых на данную дату есть еда
        List<Restaurant> restaurants = restaurantRepository.getActiveByDate(ld);

        //по каждому ресторану делаем getMenu
        Map<Restaurant, Menu> menus = new HashMap<>();
        for (Restaurant restaurant: restaurants){
            menus.put(restaurant, menuRepository.get(userId, restaurant.id(), ld));
        }
        return new HashSet<>(menus.values());*/

      /*  List<Meal> meals = mealRepository.getByDate(userId, ld);
        for(Meal m: meals){
            List<Meal> mealList = new ArrayList<>();
            mealList.add(m);
            menus.merge(m.getRestaurant(), mealList, (oldVal, newVal) -> {
                oldVal.addAll(newVal);
                return oldVal;
            } );
        }

        List<Menu> menuList = new ArrayList<>();
        menus.forEach((k, v) -> menuList.add(new Menu(k, v)));
        return menuList;*/
    }

    public List<Menu> getAllByRestaurant(int userId, int restaurantId){
        List<Menu> list = menuRepository.getAllByRestaurant(userId, restaurantId);
        return list;
    }

    public Menu get(int userId, int restaurantId, LocalDate ld){
        return menuRepository.get(userId,  restaurantId, ld);
    }
}
