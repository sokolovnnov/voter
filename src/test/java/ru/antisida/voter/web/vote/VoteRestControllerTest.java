package ru.antisida.voter.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.VoteTestData;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.service.VoteService;
import ru.antisida.voter.web.AbstractControllerTest;
import ru.antisida.voter.web.json.JsonUtil;

import java.time.LocalDateTime;

class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService voteService;

    @Test
    void create() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post("/rest/vote")
                .with(userHttpBasic(UserTestData.user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)));
        Vote createdVote = VoteTestData.MATCHER.readFromJson(actions);
        int id = createdVote.id();
        newVote.setId(id);
        VoteTestData.MATCHER.assertMatch(createdVote, newVote);
        VoteTestData.MATCHER.assertMatch(voteService.get(id, 12000), newVote);
    }

    @Test
    void getLastToday() throws Exception {
        Vote newVOne = voteService.create(new Vote(1008,
                LocalDateTime.now(),
                RestaurantsTestData.RESTAURANT_2.id(), UserTestData.user.id(), true), UserTestData.userId);
        Vote newVTwo = voteService.create(new Vote(1009,
                LocalDateTime.now(),
                RestaurantsTestData.RESTAURANT_2.id(), UserTestData.user.id(), true), UserTestData.userId);

        perform(MockMvcRequestBuilders.get("/rest/vote/last/today")
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER.contentJson(newVTwo));
    }

    @Test
    void getLastByDay() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/vote/last").param("ld", "2021-05-21")
                .with(userHttpBasic(UserTestData.user)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(VoteTestData.MATCHER.contentJson(VoteTestData.vote01));
    }

    @Test
    void getResult() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/vote/result")
                .param("ld", "2021-05-21")
                .with(userHttpBasic(UserTestData.user)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(VoteTestData.VOTE_RESULT_MATCHER.contentJson(VoteTestData.voteResult01, VoteTestData.voteResult02));
    }
}