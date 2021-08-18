package ru.antisida.voter.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/profile/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {

}
