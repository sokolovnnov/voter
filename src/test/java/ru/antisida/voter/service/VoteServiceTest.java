package ru.antisida.voter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.VoteTestData;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.util.NotFoundException;

import java.util.List;

import static org.junit.Assert.*;
import static ru.antisida.voter.VoteTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteServiceTest {

    @Autowired
    private VoteService service;

    @Test
    public void get() {
        service.get(1000, 12000);
        MATCHER.assertMatch(service.get(1000, 12000), vote01);
    }

    @Test
    public void deactivate() {
        service.deactivate(vote01.id(), UserTestData.user.id());
        Vote deactivated = service.get(vote01.id(), UserTestData.user.id());
        MATCHER.assertMatch(deactivated, getDeactivated());
    }

    @Test
    public void create() {
        Vote created = service.create(getNew(), UserTestData.user.id());
        int id = created.id();
        Vote newVote = getNew();
        newVote.setId(id);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(service.get(id, UserTestData.user.id()), newVote);
    }

    @Test
    public void delete() {
        service.delete(vote01.id(), UserTestData.user.id());
        assertThrows(NotFoundException.class, () -> service.get(vote01.id(), UserTestData.user.id()));
    }

    @Test
    public void getAllByUser() {
        List<Vote> votes = service.getAllByUser(UserTestData.user.id());
        MATCHER.assertMatch(votes, vote01, vote03);
    }

    @Test
    public void getAllActive() {
        List<Vote> votes = service.getAllActive(UserTestData.user.id());
        MATCHER.assertMatch(votes, vote01, vote02, vote03);
    }

    @Test
    public void getLastByUser() {
        MATCHER.assertMatch(service.getLastByUser(UserTestData.user.id()), vote03);
    }

    @Test
    public void getAll() {
        List<Vote> votes = service.getAll(UserTestData.admin.id());
        MATCHER.assertMatch(votes, vote01, vote02, vote03);
    }
}