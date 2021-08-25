package ru.antisida.voter.web.user;

import org.junit.jupiter.api.BeforeAll;
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
import ru.antisida.voter.service.mappers.UserCreateToMapper;
import ru.antisida.voter.service.mappers.UserMapper;
import ru.antisida.voter.to.UserCreateTo;
import ru.antisida.voter.to.UserTo;
import ru.antisida.voter.util.NotFoundException;
import ru.antisida.voter.web.AbstractControllerTest;
import ru.antisida.voter.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class AdminUserRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;
    private static UserMapper userMapper;
    private static UserCreateToMapper userCreateToMapper;

    @BeforeAll
    static void init(){
        userMapper = UserMapper.INSTANCE;
        userCreateToMapper = UserCreateToMapper.INSTANCE;
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/user")
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.MATCHER_TO.contentJson(userMapper.toTos(List.of(UserTestData.user,
                        UserTestData.admin))));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/user/" + UserTestData.userId)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.MATCHER_TO.contentJson(userMapper.toTo(UserTestData.user)));
    }

    @Test
    void create() throws Exception {
        UserCreateTo newUserTo = userCreateToMapper.toTo(UserTestData.getNew());
        ResultActions actions = perform(MockMvcRequestBuilders.post("/rest/admin/user")
                .with(userHttpBasic(UserTestData.admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUserTo)));
        UserTo createdUserTo = UserTestData.MATCHER_TO.readFromJson(actions);
        int id = createdUserTo.id();
        newUserTo.setId(id);
        User newUser = userCreateToMapper.toEntity(newUserTo);
        User createdUser = userMapper.toEntity(createdUserTo);
        UserTestData.MATCHER.assertMatch(createdUser, newUser);
        UserTestData.MATCHER.assertMatch(userMapper.toEntity(userService.get(id)), newUser);
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
    void update() throws Exception {
        User updated = UserTestData.getUpdated();
        UserTo userUpdatedTo = userMapper.toTo(updated);
        perform(MockMvcRequestBuilders.put("/rest/admin/user/" + UserTestData.userId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(JsonUtil.writeValue(userUpdatedTo)))
                .andExpect(status().isNoContent());
        UserTestData.MATCHER.assertMatch(userMapper.toEntity(userService.get(UserTestData.userId)), updated);
    }

    @Test
    void getByMail() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/admin/user/" + "by?email=" + UserTestData.admin.getEmail())
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.MATCHER_TO.contentJson(userMapper.toTo(UserTestData.admin)));
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