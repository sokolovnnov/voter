package ru.antisida.voter.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.service.RestaurantService;
import ru.antisida.voter.util.NotFoundException;
import ru.antisida.voter.web.AbstractControllerTest;
import ru.antisida.voter.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/admin/restaurant" + "/" + RestaurantsTestData.RESTAURANT_1_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RestaurantsTestData.RESTAURANT_1_ID));
    }

    @Test
//fixme
    void update() {
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantsTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post("/rest/admin/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)));
        Restaurant created = RestaurantsTestData.MATCHER.readFromJson(action);
        int newRestaurantId = created.id();
        newRestaurant.setId(newRestaurantId);
        RestaurantsTestData.MATCHER.assertMatch(newRestaurant, created);
        RestaurantsTestData.MATCHER.assertMatch(restaurantService.get(newRestaurantId), newRestaurant);
    }
}