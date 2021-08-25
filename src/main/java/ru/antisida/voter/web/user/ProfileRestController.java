package ru.antisida.voter.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.antisida.voter.service.UserService;
import ru.antisida.voter.to.UserCreateTo;
import ru.antisida.voter.to.UserTo;
import ru.antisida.voter.web.SecurityUtil;

import static ru.antisida.voter.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = "/rest/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {

    private final UserService userService;

    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo get() {
        return userService.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        userService.delete(authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserCreateTo userCreateTo) {
        userCreateTo.setRoles(SecurityUtil.get().getUserTo().getRoles());
        userService.update(userCreateTo, authUserId());
    }
}
