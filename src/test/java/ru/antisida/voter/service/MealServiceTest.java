package ru.antisida.voter.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.util.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

import static ru.antisida.voter.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        MATCHER.assertMatch(service.get(1003, 12000), meal01);
    }

    @Test
    public void create() {
        Meal created = getCreated();
        Meal saved = service.create(created, 12_000);
        int id = saved.id();
        created.setId(id);
        MATCHER.assertMatch(created, service.get(id, 12_000));
    }

    @Test
    public void delete() {
        service.delete(1003, 12_000);
        assertThrows(NotFoundException.class, () -> service.get(1003, 12_000));
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(12_000);
        MATCHER.assertMatch(mealList, meals);
    }

    @Test
    public void getAllByDate() {
        List<Meal> mealList = service.getAllByDate(12_000, LocalDate.of(2020, Month.JANUARY, 30));
        MATCHER.assertMatch(mealList, meals);
    }

    @Test
    public void getAllByRestaurant() {
        List<Meal> mealList = service.getAllByRestaurant(12_000, 10_000);
        MATCHER.assertMatch(mealList, meal01, meal02, meal03);
    }
}