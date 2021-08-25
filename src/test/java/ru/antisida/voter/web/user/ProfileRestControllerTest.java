package ru.antisida.voter.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import ru.antisida.voter.web.AbstractControllerTest;
import ru.antisida.voter.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    UserService userService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/profile")
                .with(userHttpBasic(UserTestData.user)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(UserTestData.MATCHER_TO.contentJson(UserMapper.INSTANCE.toTo(UserTestData.user)));
    }

    @Test
    void delete() throws Exception{
        perform(MockMvcRequestBuilders.delete("/rest/profile")
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        UserTestData.MATCHER_TO.assertMatch(userService.getAll(), UserMapper.INSTANCE.toTo(UserTestData.admin));
    }

    @Test
    void update() throws Exception {
        UserCreateTo updated = UserCreateToMapper.INSTANCE.toTo(UserTestData.getUpdated());
        User user = UserCreateToMapper.INSTANCE.toEntity(updated);
        perform(MockMvcRequestBuilders.put("/rest/profile").with(userHttpBasic(UserTestData.user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        int id = updated.getId();
        UserTo created = userService.get(id);
        UserTestData.MATCHER_TO.assertMatch(created, UserMapper.INSTANCE.toTo(user));
    }
}