package ru.antisida.voter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.util.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static ru.antisida.voter.RestaurantsTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void get() {
        Restaurant actual = service.get(RESTAURANT_1_ID);
        MATCHER.assertMatch(actual, RESTAURANT_1);//assertThat(actual).usingRecursiveComparison().isEqualTo
        // (RestaurantsTestData.restaurant_1);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_1_ID);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_1_ID));
    }

    @Test
    void getActiveByDate() {
        MATCHER.assertMatch(service.getActiveByDate(LocalDate.of(2020, Month.JANUARY, 30)), RESTAURANTS);
    }

    @Test
    void getAll() {
        MATCHER.assertMatch(service.getAll(), RESTAURANTS);
    }

    @Test
    //fixme почему то сначала идет запрос на селект из базы, а потом уже апдейт
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated, UserTestData.userId);
        Restaurant actual = service.get(RESTAURANT_1_ID);
        MATCHER.assertMatch(actual, getUpdated());
    }

    @Test
    void create() {
        Restaurant newRestaurant = new Restaurant(null, "New restaurant");
        service.create(newRestaurant);
        int newRestaurantId = newRestaurant.id();
        MATCHER.assertMatch(service.get(newRestaurantId), newRestaurant);
    }
}