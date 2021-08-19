package ru.antisida.voter.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.UserTestData;
import ru.antisida.voter.model.User;
import ru.antisida.voter.service.UserService;
import ru.antisida.voter.to.UserTo;
import ru.antisida.voter.util.NotFoundException;
import ru.antisida.voter.web.AbstractControllerTest;
import ru.antisida.voter.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminUserRestControllerTest extends AbstractControllerTest {

    @Autowired
    UserService userService;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/user").with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.MATCHER.contentJson(UserTestData.user, UserTestData.admin));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/user/" + UserTestData.userId)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.MATCHER.contentJson(UserTestData.user));
    }

    @Test
    void create() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post("/rest/admin/user")
                .with(userHttpBasic(UserTestData.admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(UserTestData.getNew())));
        User createdUser = UserTestData.MATCHER.readFromJson(actions);
        int id = createdUser.id();
        newUser.setId(id);
        UserTestData.MATCHER.assertMatch(createdUser, newUser);
        UserTestData.MATCHER.assertMatch(userService.get(id), newUser);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/admin/user/" + UserTestData.userId)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertThrows(NotFoundException.class, () -> userService.get(UserTestData.userId));
    }

    @Test
    @Transactional
    void update() throws Exception {//fixme userTo
        User updated = UserTestData.getUpdated();
        UserTo userTo = UserTo.asTo(updated);
        perform(MockMvcRequestBuilders.put("/rest/admin/user/" + UserTestData.userId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(JsonUtil.writeValue(userTo)))
                .andExpect(status().isNoContent());

        User a = UserTo.createNewFromTo(userTo);
        a.setId(12000);
        UserTestData.MATCHER.assertMatch(userService.get(UserTestData.userId), a);
    }

    @Test
    void getByMail() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/user/" + "by?email=" + UserTestData.admin.getEmail())
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.MATCHER.contentJson(UserTestData.admin));
    }

    @Test
    void unAuth() throws Exception{
        perform(MockMvcRequestBuilders.get("/rest/admin/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/user")
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(status().isForbidden());
    }
}