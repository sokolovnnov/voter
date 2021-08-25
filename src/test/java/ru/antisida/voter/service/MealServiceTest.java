package ru.antisida.voter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.service.mappers.MealMapper;
import ru.antisida.voter.to.MealTo;
import ru.antisida.voter.util.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.antisida.voter.MealTestData.*;

public class MealServiceTest extends AbstractServiceTest {//done

    @Autowired
    private MealService service;

    @Autowired
    private MealMapper mealMapper;

    @Test
    void get() {
        MATCHER_TO.assertMatch(service.get(meal01Id), mealMapper.toTo(meal01));
    }

    @Test
    void create() {
        MealTo createdTo = mealMapper.toTo(getNew());
        MealTo savedTo = service.create(createdTo);
        int newId = savedTo.id();
        createdTo.setId(newId);
        MATCHER_TO.assertMatch(createdTo, service.get(newId));
    }

    @Test
    void delete() {
        service.delete(meal01Id);
        assertThrows(NotFoundException.class, () -> service.get(meal01Id));
    }

    @Test
    void getAll() {
        List<MealTo> mealListTo = service.getAll();
        MATCHER_TO.assertMatch(mealListTo, mealMapper.toTos(meals));
    }

    @Test
    void getAllByDate() {
        List<MealTo> mealToList = service.getAllByDate(LocalDate.of(2020, Month.JANUARY, 30));
        MATCHER_TO.assertMatch(mealToList, mealMapper.toTos(meals));
    }

    @Test
    void getAllByRestaurant() {
        List<MealTo> mealToList = service.getAllByRestaurant(RestaurantsTestData.RESTAURANT_1_ID);
        MATCHER_TO.assertMatch(mealToList, mealMapper.toTos(List.of(meal01, meal02, meal03)));
    }

    @Test
    void createWithException(){
        validateRootCause(ConstraintViolationException.class, () -> service.create(mealMapper.toTo(new Meal(null,
                null,
                "sfsdf", 1, RestaurantsTestData.RESTAURANT_1))));
        validateRootCause(ConstraintViolationException.class, () -> service.create(mealMapper.toTo(new Meal(null,
                LocalDate.of(2021, Month.MAY, 21),
                "", 1, RestaurantsTestData.RESTAURANT_1))));
        validateRootCause(ConstraintViolationException.class, () -> service.create(mealMapper.toTo(new Meal(null,
                LocalDate.of(2021, Month.MAY, 21),
                "asdf", 1, null))));
    }
}