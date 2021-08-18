package ru.antisida.voter.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.VoteTestData;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.service.VoteService;
import ru.antisida.voter.web.AbstractControllerTest;

import java.time.LocalDateTime;

class AdminVoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService voteService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/vote" + "/" + VoteTestData.vote01.id())
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER.contentJson(VoteTestData.vote01));
    }

    @Test
    void getHistoryByUser() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/vote/user" + "/" + 12000)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER.contentJson(VoteTestData.vote00, VoteTestData.vote01, VoteTestData.vote03));
    }

    @Test
    @Transactional
    void getAllActiveToday() throws Exception {
        Vote newVOne = voteService.create(new Vote(1008,
                LocalDateTime.now(),
                RestaurantsTestData.RESTAURANT_2.id(), UserTestData.user.id(), true), 12000);
        Vote newVTwo = voteService.create(new Vote(1009,
                LocalDateTime.now(),
                RestaurantsTestData.RESTAURANT_2.id(), UserTestData.admin.id(), true), 11000);

        perform(MockMvcRequestBuilders.get("/rest/admin/vote/active")
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER.contentJson(newVOne, newVTwo));
    }

    @Test
    void getActiveByUserByDate() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/vote/user" + "/" + 12000 + "/last")
                .param("ld", "2021-05-21")
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER.contentJson(VoteTestData.vote01));
    }
}