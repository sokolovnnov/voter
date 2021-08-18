package ru.antisida.voter;

import ru.antisida.voter.model.Meal;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class MealTestData {

    public static final MatcherFactory<Meal> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class,
            "restaurant");

    public final static int meal01Id = 1003;


    public static Meal meal01 = new Meal(1003, LocalDate.of(2020, 1, 30), "Чай10_000", 1,
            RestaurantsTestData.RESTAURANT_1);
    public static Meal meal02 = new Meal(1004, LocalDate.of(2020, 1, 30), "Каша10_000", 2,
            RestaurantsTestData.RESTAURANT_1);
    public static Meal meal03 = new Meal(1005, LocalDate.of(2020, 1, 30), "Бутерброд10_000", 3,
            RestaurantsTestData.RESTAURANT_1);
    public static Meal meal04 = new Meal(1006, LocalDate.of(2020, 1, 30), "Кофе10_001", 11,
            RestaurantsTestData.RESTAURANT_2);
    public static Meal meal05 = new Meal(1007, LocalDate.of(2020, 1, 30), "Плов10_001", 22,
            RestaurantsTestData.RESTAURANT_2);
    public static Meal meal06 = new Meal(1008, LocalDate.of(2020, 1, 30), "Конфета10_001", 33,
            RestaurantsTestData.RESTAURANT_2);

    public static List<Meal> meals = List.of(meal01, meal02, meal03, meal04, meal05, meal06);

    public static Meal getNew() {
        return new Meal(null, LocalDate.of(2020, Month.JANUARY, 31), "Created meal", 1000, RestaurantsTestData.RESTAURANT_1);
    }
}
