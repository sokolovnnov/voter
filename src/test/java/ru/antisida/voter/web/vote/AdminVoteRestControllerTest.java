package ru.antisida.voter.web.vote;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.VoteTestData;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.service.VoteService;
import ru.antisida.voter.service.mappers.VoteMapper;
import ru.antisida.voter.to.VoteTo;
import ru.antisida.voter.web.AbstractControllerTest;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
class AdminVoteRestControllerTest extends AbstractControllerTest {//done

    @Autowired
    private VoteService voteService;
    private static VoteMapper voteMapper;

    @BeforeAll
    static void init() {
        voteMapper = VoteMapper.INSTANCE;
    }

    @Test
    void get() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.get("/rest/admin/vote" + "/" + VoteTestData.vote01.id())
                .with(userHttpBasic(UserTestData.admin)))
                .andDo(MockMvcResultHandlers.print());
        VoteTo createdTo = VoteTestData.MATCHER_TO.readFromJson(actions);
        Vote created = voteMapper.toEntity(createdTo);
        VoteTestData.MATCHER.assertMatch(created, VoteTestData.vote01);

        perform(MockMvcRequestBuilders.get("/rest/admin/vote" + "/" + VoteTestData.vote01.id())
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//                .andExpect(VoteTestData.MATCHER_TO.contentJson(voteMapper.toTo(VoteTestData.vote01)));
    }

    @Test
    void getHistoryByUser() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/vote/user" + "/" + 12000)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER_TO.contentJson(voteMapper.toTos(
                        List.of(VoteTestData.vote00, VoteTestData.vote01, VoteTestData.vote03))));
    }

    @Test
    @Transactional
    void getAllActiveToday() throws Exception {
        VoteTo newVOneTo = voteService.create(voteMapper.toTo(new Vote(1008,
                LocalDateTime.now(),
                RestaurantsTestData.RESTAURANT_2, UserTestData.user, true)), 12000);
        VoteTo newVTwoTo = voteService.create(voteMapper.toTo(new Vote(1009,
                LocalDateTime.now(),
                RestaurantsTestData.RESTAURANT_2, UserTestData.admin, true)), 11000);

        perform(MockMvcRequestBuilders.get("/rest/admin/vote/active")
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER_TO.contentJson(newVOneTo, newVTwoTo));
    }

    @Test
    void getActiveByUserByDate() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/vote/user" + "/" + 12000 + "/last")
                .param("ld", "2021-05-21")
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER_TO.contentJson(voteMapper.toTo(VoteTestData.vote01)));
    }
}