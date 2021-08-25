package ru.antisida.voter.web.meal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.MealTestData;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.service.MealService;
import ru.antisida.voter.service.mappers.MealMapper;
import ru.antisida.voter.to.MealTo;
import ru.antisida.voter.util.NotFoundException;
import ru.antisida.voter.web.AbstractControllerTest;
import ru.antisida.voter.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class AdminMealRestControllerTest extends AbstractControllerTest {//done

    @Autowired
    private MealService mealService;
    private static MealMapper mealMapper;

    @BeforeAll
    static void init(){
        mealMapper = MealMapper.INSTANCE;
    }

    @Test
    void create() throws Exception {
        MealTo newMealTo = mealMapper.toTo(MealTestData.getNew());
        ResultActions actions = perform(MockMvcRequestBuilders.post("/rest/admin/meal")
                .with(userHttpBasic(UserTestData.admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMealTo)))
                .andDo(MockMvcResultHandlers.print());
        MealTo createdTo = MealTestData.MATCHER_TO.readFromJson(actions);
        int newId = createdTo.id();
        newMealTo.setId(newId);
        Meal newMeal = mealMapper.toEntity(newMealTo);
        Meal created = mealMapper.toEntity(createdTo);
        MealTestData.MATCHER.assertMatch(created, newMeal);
        MealTestData.MATCHER.assertMatch(mealMapper.toEntity(mealService.get(newId)), newMeal);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/meal" + "/" + MealTestData.meal01.id())
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER_TO.contentJson(mealMapper.toTo(MealTestData.meal01)));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/admin/meal" + "/" + 1003)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(1003));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/meal")
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER_TO.contentJson(mealMapper.toTos(MealTestData.meals)));
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/meal/date").param("ld", "2020-01-30")
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER_TO.contentJson(mealMapper.toTos(MealTestData.meals)));
    }

    @Test
    void getAllByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/meal/restaurant/" + RestaurantsTestData.RESTAURANT_1_ID)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER_TO.contentJson(mealMapper
                        .toTos(List.of(MealTestData.meal01, MealTestData.meal02, MealTestData.meal03))));
    }
}