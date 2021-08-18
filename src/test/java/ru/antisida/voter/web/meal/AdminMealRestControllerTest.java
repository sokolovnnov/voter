package ru.antisida.voter.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.MealTestData;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.service.MealService;
import ru.antisida.voter.util.NotFoundException;
import ru.antisida.voter.web.AbstractControllerTest;
import ru.antisida.voter.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AdminMealRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MealService mealService;

    @Test
    @Transactional
    void create() throws Exception {
        Meal newMeal = MealTestData.getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post("/rest/meal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)));
        Meal created = MealTestData.MATCHER.readFromJson(actions);
        int newId = created.id();
        newMeal.setId(newId);
        MealTestData.MATCHER.assertMatch(created, newMeal);
        MealTestData.MATCHER.assertMatch(mealService.get(newId), newMeal);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/meal" + "/" + 1003))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(1003));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/meal"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER.contentJson(MealTestData.meals));
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/meal/date").param("ld", "2020-01-30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER.contentJson(MealTestData.meals));
    }

    @Test//fixme
    void getAllByRestaurant() {
    }
}