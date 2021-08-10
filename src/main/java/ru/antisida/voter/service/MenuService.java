package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.repo.datajpa.DataJpaMealRepo;
import ru.antisida.voter.to.Menu;
import ru.antisida.voter.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final DataJpaMealRepo mealRepository;

    public MenuService(DataJpaMealRepo mealRepository) {
        this.mealRepository = mealRepository;
    }

//fixme при создании меню каждый раз идет запрос к базе, чтобы вытянуть ресторан. Чтобы такого не было:
    //fixme надо вытягивать и еду и рестораны одним запросом и сопоставлять их в репозитории
    public List<Menu> getAllByDate(LocalDate ld){
        return MenuUtil.toDateListMenu(mealRepository.getAllByDate(ld));
    }

    public List<Menu> getAllByRestaurant(int restaurantId){
        return MenuUtil.toRestaurantListMenu(mealRepository.getAllByRestaurant(restaurantId));
    }

    public Menu get(int restaurantId, LocalDate ld){
        return MenuUtil.toMenu(mealRepository.getAllByRestaurantAndByDate(restaurantId, ld));
    }
}
