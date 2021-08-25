package ru.antisida.voter;

import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.to.RestaurantTo;

import java.util.List;

public class RestaurantsTestData {

    public static final MatcherFactory<Restaurant> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class,
                    "meals");
    public static final MatcherFactory<RestaurantTo> MATCHER_TO =
            MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class,
                    "meals");

    public static final Restaurant RESTAURANT_1 = new Restaurant(10_000, "Ресторан10_000");
    public static final Restaurant RESTAURANT_2 = new Restaurant(10_001, "Ресторан10_001");

    public static final RestaurantTo RESTAURANT_1_TO = new RestaurantTo(10_000, "Ресторан10_000");
    public static final RestaurantTo RESTAURANT_2_TO = new RestaurantTo(10_001, "Ресторан10_001");

    public static final int RESTAURANT_1_ID = 10_000;
    public static final int RESTAURANT_2_ID = 10_001;

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_1, RESTAURANT_2);

    public static final List<RestaurantTo> RESTAURANTS_TO = List.of(RESTAURANT_1_TO, RESTAURANT_2_TO);

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        updated.setId(RESTAURANT_1.id());
        return updated;
    }

    public static RestaurantTo getUpdatedTo() {
        Restaurant updated = getUpdated();
        return new RestaurantTo(updated.id(), updated.getName());
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }

    public static RestaurantTo getNewTo() {
        Restaurant newRestaurant = getNew();
        return new RestaurantTo(newRestaurant.getId(), newRestaurant.getName());
    }
}
