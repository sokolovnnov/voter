package ru.antisida.voter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.RestaurantsTestData;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.service.mappers.VoteMapper;
import ru.antisida.voter.to.VoteTo;
import ru.antisida.voter.util.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.antisida.voter.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    private static VoteMapper voteMapper;

    @BeforeAll
    static void init() {
        voteMapper = VoteMapper.INSTANCE;
    }

    @Test
    void get() {
        MATCHER.assertMatch(voteMapper.toEntity(service.get(vote01Id, UserTestData.userId)), vote01);
    }

    @Test
    @Transactional
    void create() {
        VoteTo created = service.create(voteMapper.toTo(getNew()), UserTestData.userId);
        int id = created.getId();
        Vote newVote = getNew();
        newVote.setId(id);
        MATCHER.assertMatch(voteMapper.toEntity(created), newVote);
        MATCHER.assertMatch(voteMapper.toEntity(service.get(id, UserTestData.userId)), newVote);
    }

    @Test
    void delete() {
        service.delete(vote01.id());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(vote01.id(), UserTestData.userId));
    }

    @Test
    void getAllByUser() {
        List<Vote> votes = voteMapper.toListEntity(service.getAllByUser(UserTestData.userId));
        MATCHER.assertMatch(votes, vote00, vote01, vote03);
    }

    @Test
    void getAllActiveByDate() {
        List<Vote> votes = voteMapper.toListEntity(service.getAllActiveByDate(LocalDate.of(2021, Month.MAY,
                21)));
        MATCHER.assertMatch(votes, vote01, vote02);
    }

    @Test
    void getLastByUser() {
        MATCHER.assertMatch(voteMapper.toEntity(
                service.getActiveByUserByDate(UserTestData.userId, LocalDate.of(2021, Month.JUNE, 21))),
                vote03);
    }

    @Test
    void getAll() {
        List<Vote> votes = voteMapper.toListEntity(service.getAll());
        MATCHER.assertMatch(votes, vote00, vote01, vote02, vote03);
    }

    @Test
    void createWithException1() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(
                voteMapper.toTo(new Vote(null, null, RestaurantsTestData.RESTAURANT_1, UserTestData.user, true)),
                12000));
    }
}