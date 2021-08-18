package ru.antisida.voter.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.antisida.voter.model.User;
import ru.antisida.voter.service.UserService;
import ru.antisida.voter.to.UserTo;
import ru.antisida.voter.util.ValidationUtil;
import ru.antisida.voter.web.SecurityUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return userService.get(SecurityUtil.authUserId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = userService.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/user/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        userService.delete(SecurityUtil.authUserId());
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @PathVariable int id) {
        User user = get(userTo.id());
        User updated = UserTo.updateFromTo(user, userTo);
        ValidationUtil.assureIdConsistent(updated, SecurityUtil.authUserId());
        userService.update(user);
    }

    @GetMapping("/by")
    public User getByMail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/text")
    public String testUTF() {
        return "Русский текст";
    }


}
