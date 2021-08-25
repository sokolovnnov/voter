package ru.antisida.voter.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.service.RestaurantService;
import ru.antisida.voter.to.RestaurantTo;
import ru.antisida.voter.util.NotFoundException;
import ru.antisida.voter.web.AbstractControllerTest;
import ru.antisida.voter.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class AdminRestaurantRestControllerTest extends AbstractControllerTest {//done

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/admin/restaurant" + "/" + RestaurantsTestData.RESTAURANT_1_ID)
                .with(userHttpBasic(UserTestData.admin)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RestaurantsTestData.RESTAURANT_1_ID));
    }

    @Test
    void update() throws Exception {
        RestaurantTo updatedRestaurantTo = RestaurantsTestData.getUpdatedTo();
        perform(MockMvcRequestBuilders.put("/rest/admin/restaurant" + "/" + RestaurantsTestData.RESTAURANT_1_ID)
                .with(userHttpBasic(UserTestData.admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurantTo)))
                .andExpect(status().isNoContent());
        RestaurantsTestData.MATCHER_TO.assertMatch(restaurantService.get(RestaurantsTestData.RESTAURANT_1_ID),
                updatedRestaurantTo);
    }

    @Test
    void create() throws Exception {
        RestaurantTo newRestaurantTo = RestaurantsTestData.getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post("/rest/admin/restaurant")
                .with(userHttpBasic(UserTestData.admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurantTo)))
                .andDo(MockMvcResultHandlers.print());
        RestaurantTo createdTo = RestaurantsTestData.MATCHER_TO.readFromJson(action);
        int newRestaurantId = createdTo.id();
        newRestaurantTo.setId(newRestaurantId);
        RestaurantsTestData.MATCHER_TO.assertMatch(newRestaurantTo, createdTo);
        RestaurantsTestData.MATCHER_TO.assertMatch(restaurantService.get(newRestaurantId), newRestaurantTo);
    }

    @Test
    void unAuth() throws Exception{
        perform(MockMvcRequestBuilders.delete("/rest/admin/restaurant" + "/" + RestaurantsTestData.RESTAURANT_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/admin/restaurant" + "/" + RestaurantsTestData.RESTAURANT_1_ID)
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(status().isForbidden());
    }
}