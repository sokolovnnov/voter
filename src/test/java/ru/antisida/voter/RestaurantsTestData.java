package ru.antisida.voter;

import ru.antisida.voter.model.Restaurant;

import java.util.List;

public class RestaurantsTestData {

    public static final MatcherFactory<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("meals");

    public static final Restaurant restaurant_1 = new Restaurant(10_000, "Ресторан10_000");
    public static final Restaurant restaurant_2 = new Restaurant(10_001, "Ресторан10_001");

    public static List<Restaurant> restaurants = List.of(restaurant_1, restaurant_2);

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant_1);
        updated.setName("UpdatedName");
        updated.setId(restaurant_1.id());
        return updated;
    }
}
