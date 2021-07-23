package ru.antisida.voter;

import ru.antisida.voter.to.Menu;

import java.time.LocalDate;
import java.util.List;

public class MenuTestData {

    public static final MatcherFactory<Menu> MATCHER = MatcherFactory.usingIgnoringFieldsComparator();

    public static Menu restaurant01Menu = new Menu(RestaurantsTestData.restaurant_1,
            List.of(MealTestData.meal01, MealTestData.meal02, MealTestData.meal03), LocalDate.of(2020, 1, 30));
    public static Menu restaurant02Menu = new Menu(RestaurantsTestData.restaurant_2,
            List.of(MealTestData.meal04, MealTestData.meal05, MealTestData.meal06), LocalDate.of(2020, 1, 30));

}
