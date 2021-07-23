package ru.antisida.voter.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.antisida.voter.MatcherFactory;
import ru.antisida.voter.MenuTestData;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.to.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.antisida.voter.MenuTestData.*;
import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MenuServiceTest {

    @Autowired
    private MenuService service;

    @Test
    public void getAllByDate() {
        List<Menu> menuList = service.getAllByDate(12_000, LocalDate.of(2020, Month.JANUARY, 30));
        MATCHER.assertMatch(menuList, restaurant01Menu, restaurant02Menu);
    }

    @Test
    public void getAllByRestaurant() {
        List<Menu> menuList = service.getAllByRestaurant(12_000, 10_000);
        Menu expMenu = restaurant01Menu;
        MATCHER.assertMatch(menuList, expMenu);
    }

    @Test
    public void get() {
        Menu menu = service.get(12_000, 10_000, LocalDate.of(2020, Month.JANUARY, 30));
        Menu expMenu = restaurant01Menu;
        MATCHER.assertMatch(menu, expMenu);
    }
}