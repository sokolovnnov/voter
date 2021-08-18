package ru.antisida.voter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.util.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.antisida.voter.MealTestData.*;

public class MealServiceTest extends AbstractServiceTest {

    @Autowired
    private MealService service;

    @Test
    void get() {
        MATCHER.assertMatch(service.get(meal01Id), meal01);
    }

    @Test
    void create() {
        Meal created = getNew();
        Meal saved = service.create(created);
        int id = saved.id();
        created.setId(id);
        MATCHER.assertMatch(created, service.get(id));
    }

    @Test
    void delete() {
        service.delete(meal01Id);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(meal01Id));
    }

    @Test
    void getAll() {
        List<Meal> mealList = service.getAll();
        MATCHER.assertMatch(mealList, meals);
    }

    @Test
    void getAllByDate() {
        List<Meal> mealList = service.getAllByDate(LocalDate.of(2020, Month.JANUARY, 30));
        MATCHER.assertMatch(mealList, meals);
    }

    @Test
    void getAllByRestaurant() {
        List<Meal> mealList = service.getAllByRestaurant(RestaurantsTestData.RESTAURANT_1_ID);
        MATCHER.assertMatch(mealList, meal01, meal02, meal03);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Meal(null, null,
                "sfsdf", 1 , RestaurantsTestData.RESTAURANT_1)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Meal(null,
                LocalDate.of(2021, Month.MAY, 21),
                "", 1 , RestaurantsTestData.RESTAURANT_1)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Meal(null,
                LocalDate.of(2021, Month.MAY, 21),
                "asdf", 1 , null)));
    }
}