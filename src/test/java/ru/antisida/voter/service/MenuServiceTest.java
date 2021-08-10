package ru.antisida.voter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.antisida.voter.to.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.antisida.voter.MenuTestData.*;


public class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setup() {
        cacheManager.getCache("dateMenu").clear();
        cacheManager.getCache("restaurantMenu").clear();
        cacheManager.getCache("menu").clear();
    }

    @Test
    public void getAllByDate() {
        List<Menu> menuList = service.getAllByDate(LocalDate.of(2020, Month.JANUARY, 30));
        MATCHER.assertMatch(menuList, restaurant01Menu, restaurant02Menu);
//        List<Menu> menuList1 = service.getAllByDate(LocalDate.of(2020, Month.JANUARY, 30));
//        MATCHER.assertMatch(menuList1, restaurant01Menu, restaurant02Menu);
    }

    @Test
    public void getAllByRestaurant() {
        List<Menu> menuList = service.getAllByRestaurant(10_000);
        Menu expMenu = restaurant01Menu;
        MATCHER.assertMatch(menuList, expMenu);
    }

    @Test
    public void get() {
        Menu menu = service.get(10_000,  LocalDate.of(2020, Month.JANUARY, 30));
        Menu expMenu = restaurant01Menu;
        MATCHER.assertMatch(menu, expMenu);
    }
}