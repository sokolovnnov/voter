package ru.antisida.voter.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.util.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static ru.antisida.voter.RestaurantsTestData.*;



public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void get() {
        Restaurant actual = service.get(Restaurant1Id, UserTestData.userId);
        MATCHER.assertMatch(actual, restaurant_1);//assertThat(actual).usingRecursiveComparison().isEqualTo
        // (RestaurantsTestData.restaurant_1);
    }

    @Test
    public void delete() {
        service.delete(Restaurant1Id, UserTestData.userId);
        assertThrows(NotFoundException.class, () -> service.get(Restaurant1Id, UserTestData.userId));
    }

    @Test
    public void getActiveByDate() {
        MATCHER.assertMatch(service.getActiveByDate(LocalDate.of(2020, Month.JANUARY, 30)), restaurants);
    }

    @Test
    public void getAll() {
        MATCHER.assertMatch(service.getAll(UserTestData.userId), restaurants);
    }

    @Test
    //fixme почему то сначала идет запрос на селект из базы, а потом уже апдейт
    public void update() {
        Restaurant updated = getUpdated();
        service.update(updated, UserTestData.userId);
        Restaurant actual = service.get(Restaurant1Id, UserTestData.userId);
        MATCHER.assertMatch(actual, getUpdated());
    }

    @Test
    public void create() {
        Restaurant newRestaurant = new Restaurant(null, "New restaurant");
        service.create(newRestaurant, UserTestData.userId);
        int id = newRestaurant.id();
        MATCHER.assertMatch(service.get(id, UserTestData.userId), newRestaurant);
    }
}