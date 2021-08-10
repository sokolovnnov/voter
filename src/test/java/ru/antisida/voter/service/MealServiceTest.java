package ru.antisida.voter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.util.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
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
        MATCHER.assertMatch(service.get(meal01Id), meal01);
    }

    @Test
    public void create() {
        Meal created = getCreated();
        Meal saved = service.create(created, UserTestData.userId);
        int id = saved.id();
        created.setId(id);
        MATCHER.assertMatch(created, service.get(id));
    }

    @Test
    public void delete() {
        service.delete(meal01Id);
        assertThrows(NotFoundException.class, () -> service.get(meal01Id));
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(meal01Id);
        MATCHER.assertMatch(mealList, meals);
    }

    @Test
    public void getAllByDate() {
        List<Meal> mealList = service.getAllByDate(UserTestData.userId, LocalDate.of(2020, Month.JANUARY, 30));
        MATCHER.assertMatch(mealList, meals);
    }

    @Test
    public void getAllByRestaurant() {
        List<Meal> mealList = service.getAllByRestaurant(RestaurantsTestData.Restaurant1Id);
        MATCHER.assertMatch(mealList, meal01, meal02, meal03);
    }
}