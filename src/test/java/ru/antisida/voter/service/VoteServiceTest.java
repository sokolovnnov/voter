package ru.antisida.voter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.util.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static ru.antisida.voter.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void get() {
        service.get(vote01Id, UserTestData.userId);
        MATCHER.assertMatch(service.get(vote01Id, UserTestData.userId), vote01);
    }

    @Test
    void deactivate() {
        service.deactivate(vote01.id(), UserTestData.user.id());
        Vote deactivated = service.get(vote01.id(), UserTestData.user.id());
        MATCHER.assertMatch(deactivated, getDeactivated());
    }

    @Test
    void create() {
        Vote created = service.create(getNew(), UserTestData.user.id());
        int id = created.id();
        Vote newVote = getNew();
        newVote.setId(id);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(service.get(id, UserTestData.user.id()), newVote);
    }

    @Test
    void delete() {
//        List<Vote> votes1 = service.getAll(UserTestData.admin.id()); //для проверки работы кэша
        service.delete(vote01.id(), UserTestData.user.id());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(vote01.id(), UserTestData.user.id()));
//        List<Vote> votes = service.getAll(UserTestData.admin.id());
//        MATCHER.assertMatch(votes, vote01, vote02, vote03);
    }

    @Test
    void getAllByUser() {
        List<Vote> votes = service.getAllByUser(UserTestData.user.id());
        MATCHER.assertMatch(votes, vote01, vote03);
    }

    @Test
    void getAllActive() {
        List<Vote> votes = service.getAllActive(UserTestData.user.id());
        MATCHER.assertMatch(votes, vote01, vote02, vote03);
    }

    @Test
    void getLastByUser() {
        MATCHER.assertMatch(service.getLastByUser(UserTestData.user.id()), vote03);
    }

    @Test
    void getAll() {
        List<Vote> votes = service.getAll(UserTestData.admin.id());
        MATCHER.assertMatch(votes, vote01, vote02, vote03);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Vote(null, null,
//                LocalDateTime.of(2021, Month.MAY, 21, 11, 57, 17),
                10_000, UserTestData.user.id(), true ), UserTestData.user.id()));

    }
}