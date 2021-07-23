package ru.antisida.voter.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.util.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

import static ru.antisida.voter.RestaurantsTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void get() {
        Restaurant actual = service.get(10_000, 12_000);
        MATCHER.assertMatch(actual, restaurant_1);//assertThat(actual).usingRecursiveComparison().isEqualTo
        // (RestaurantsTestData.restaurant_1);
    }

    @Test
    public void delete() {
        service.delete(10000, 12000);
        assertThrows(NotFoundException.class, () -> service.get(10000, 12000));
    }

    @Test
    public void getActiveByDate() {
        MATCHER.assertMatch(service.getActiveByDate(LocalDate.of(2020, Month.JANUARY,30)), restaurants);
    }

    @Test
    public void getAll() {
        MATCHER.assertMatch(service.getAll(12000), restaurants);
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        service.update(updated,12000);
        Restaurant actual = service.get(10_000, 12_000);
        MATCHER.assertMatch(actual, getUpdated());
    }

    @Test
    public void create() {
        Restaurant newRestaurant = new Restaurant(null, "New restaurant");
        service.create(newRestaurant, 12_000);
        int id = newRestaurant.id();
        MATCHER.assertMatch(service.get(id, 12_000), newRestaurant);
    }
}