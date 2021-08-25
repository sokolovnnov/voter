package ru.antisida.voter.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.web.AbstractControllerTest;


@Transactional
public class RestaurantRestControllerTest extends AbstractControllerTest {//done

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/restaurant" + "/" + RestaurantsTestData.RESTAURANT_2_ID)
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantsTestData.MATCHER_TO.contentJson(RestaurantsTestData.RESTAURANT_2_TO));
    }

    @Test
    void getActiveByDate() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/restaurant/active").param("ld", "2020-01-30")
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantsTestData.MATCHER_TO.contentJson(RestaurantsTestData.RESTAURANTS_TO));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/restaurant")
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantsTestData.MATCHER_TO.contentJson(RestaurantsTestData.RESTAURANTS_TO));
    }
}
