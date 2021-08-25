package ru.antisida.voter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.service.mappers.RestaurantMapper;
import ru.antisida.voter.to.RestaurantTo;
import ru.antisida.voter.util.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static ru.antisida.voter.RestaurantsTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {//done

    @Autowired
    private RestaurantService service;

    @Test
    void get() {
        RestaurantTo actual = service.get(RESTAURANT_1_ID);
        MATCHER_TO.assertMatch(actual, RESTAURANT_1_TO);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_1_ID);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_1_ID));
    }

    @Test
    void getActiveByDate() {
        MATCHER_TO.assertMatch(service.getActiveByDate(LocalDate.of(2020, Month.JANUARY, 30)), RESTAURANTS_TO);
    }

    @Test
    void getAll() {
        MATCHER_TO.assertMatch(service.getAll(), RESTAURANTS_TO);
    }

    @Test
    void update() {
        RestaurantTo updated = getUpdatedTo();
        service.update(updated, RESTAURANT_1_ID);
        RestaurantTo actual = service.get(RESTAURANT_1_ID);
        MATCHER_TO.assertMatch(actual, updated);
    }

    @Test
    void create() {
        RestaurantTo newRestaurantTo = new RestaurantTo(null, "New restaurant");
        RestaurantTo createdRestaurantTo = service.create(newRestaurantTo);
        int newRestaurantId = createdRestaurantTo.id();
        newRestaurantTo.setId(newRestaurantId);
        MATCHER_TO.assertMatch(service.get(newRestaurantId), newRestaurantTo);
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class,
                () -> service.create(RestaurantMapper.INSTANCE.toTo(new Restaurant(null,""))));
    }
}