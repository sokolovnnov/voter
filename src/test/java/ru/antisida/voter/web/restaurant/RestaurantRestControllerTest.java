package ru.antisida.voter.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.service.RestaurantService;
import ru.antisida.voter.web.AbstractControllerTest;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void  get() throws Exception{
        perform(MockMvcRequestBuilders.get("/rest/restaurant" + "/" + RestaurantsTestData.RESTAURANT_2_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantsTestData.MATCHER.contentJson(RestaurantsTestData.RESTAURANT_2));
    }

    @Test
    void getActiveByDate() throws Exception{
        perform(MockMvcRequestBuilders.get("/rest/restaurant/active").param("ld", "2020-01-30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantsTestData.MATCHER.contentJson(RestaurantsTestData.RESTAURANTS));

    }

    @Test
    void  getAll() throws Exception{
        perform(MockMvcRequestBuilders.get("/rest/restaurant"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantsTestData.MATCHER.contentJson(RestaurantsTestData.RESTAURANTS));
    }
}
