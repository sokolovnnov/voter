package ru.antisida.voter.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.antisida.voter.to.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.antisida.voter.MenuTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MenuServiceTest {

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