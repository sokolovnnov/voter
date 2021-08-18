package ru.antisida.voter;

import ru.antisida.voter.model.Restaurant;

import java.util.List;

public class RestaurantsTestData {

    public static final MatcherFactory<Restaurant> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class,
            "meals");

    public static final Restaurant RESTAURANT_1 = new Restaurant(10_000, "Ресторан10_000");
    public static final Restaurant RESTAURANT_2 = new Restaurant(10_001, "Ресторан10_001");

    public static final int RESTAURANT_1_ID = 10_000;
    public static final int RESTAURANT_2_ID = 10_001;

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_1, RESTAURANT_2);

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        updated.setId(RESTAURANT_1.id());
        return updated;
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }
}
