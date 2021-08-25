package ru.antisida.voter.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.antisida.voter.service.UserService;
import ru.antisida.voter.to.UserCreateTo;
import ru.antisida.voter.to.UserTo;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserTo> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserTo get(@PathVariable int id) {
        return userService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTo> create(@RequestBody UserCreateTo userCreateTo) {
        UserTo created = userService.create(userCreateTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/user/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserCreateTo userCreateTo, @PathVariable int id) {
        userService.update(userCreateTo, id);
    }

    @GetMapping("/by")
    public UserTo getByMail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/text")
    public String testUTF() {
        return "Русский текст";
    }
}
