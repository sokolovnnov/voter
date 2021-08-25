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
import ru.antisida.voter.web.json.JsonUtil;

import java.time.LocalDateTime;

@Transactional
class VoteRestControllerTest extends AbstractControllerTest {//done

    @Autowired
    private VoteService voteService;
    private static VoteMapper voteMapper;

    @BeforeAll
    static void init() {
        voteMapper = VoteMapper.INSTANCE;
    }

    @Test
    void create() throws Exception {
        VoteTo newVoteTo = voteMapper.toTo(VoteTestData.getNew());
        ResultActions actions = perform(MockMvcRequestBuilders.post("/rest/vote")
                .with(userHttpBasic(UserTestData.user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)));
        Vote createdVote = voteMapper.toEntity(VoteTestData.MATCHER_TO.readFromJson(actions));
        int id = createdVote.id();
        newVoteTo.setId(id);
        Vote newVote = voteMapper.toEntity(newVoteTo);
        VoteTestData.MATCHER.assertMatch(createdVote, newVote);
        VoteTestData.MATCHER.assertMatch(voteMapper.toEntity(voteService.get(id, 12000)), newVote);
    }

    @Test
    void getLastToday() throws Exception {
        voteService.create(voteMapper.toTo(new Vote(1008, LocalDateTime.now(), RestaurantsTestData.RESTAURANT_2,
                UserTestData.user, true)), UserTestData.userId);
        VoteTo newTwoTo = voteService.create(voteMapper.toTo(new Vote(1009,
                LocalDateTime.now(),
                RestaurantsTestData.RESTAURANT_2, UserTestData.user, true)), UserTestData.userId);

        perform(MockMvcRequestBuilders.get("/rest/vote/last/today")
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER_TO.contentJson(newTwoTo));
    }

    @Test
    void getLastByDay() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/vote/last").param("ld", "2021-05-21")
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER_TO.contentJson(voteMapper.toTo(VoteTestData.vote01)));
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