package ru.antisida.voter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.util.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
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

//    @Test
//    void deactivate() {
//        service.deactivate(vote01.id(), UserTestData.user.id());
//        Vote deactivated = service.get(vote01.id(), UserTestData.user.id());
//        MATCHER.assertMatch(deactivated, getDeactivated());
//    }

    @Test
    void create() {
//        Vote d = service.create(
//                new Vote(null, null,
////                LocalDateTime.of(2021, Month.MAY, 21, 11, 57, 17),
//                        10_000, UserTestData.user.id(), true ), 12000);
        Vote created = service.create(getNew(), 12000);
        int id = created.getId();
        Vote newVote = getNew();
        newVote.setId(id);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(service.get(id, UserTestData.user.id()), newVote);
    }

    @Test
    void delete() {
//        List<Vote> votes1 = service.getAll(UserTestData.admin.id()); //для проверки работы кэша
        service.delete(vote01.id());
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
    void getAllActiveByDate() {
        List<Vote> votes = service.getAllActiveByDate(LocalDate.of(2021, Month.MAY, 21));
        MATCHER.assertMatch(votes, vote01, vote02);
    }

    @Test
    void getLastByUser() {
        MATCHER.assertMatch(service.getActiveByUserByDate(
                UserTestData.userId, LocalDate.of(2021, Month.JUNE, 21)),
                vote03);
    }

    @Test
    void getAll() {
        List<Vote> votes = service.getAll(UserTestData.admin.id());
        MATCHER.assertMatch(votes, vote01, vote02, vote03);
    }

    @Test
    void createWithException1()  {
//        Vote v = service.get(1000, 12000);
//        Vote v1 =
//        service.create(new Vote(null, null,
//                        10_000, UserTestData.user.id(), true ), 12000);
//        int id = v1.id();
//        Vote v2 = service.get(id, 12000);
//        System.out.println(v2.getLocalDateTime());
        validateRootCause(ConstraintViolationException.class, () -> service.create(
                new Vote(null, null,
                10_000, UserTestData.user.id(), true ), 12000));

    }
}