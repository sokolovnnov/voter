package ru.antisida.voter.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.service.VoteService;

import java.util.List;

@RestController
@RequestMapping("/vote")
public class AdminVoteRestController {

    @Autowired
    private VoteService voteService;

    @GetMapping("/user/{userId}")
    public List<Vote> getAllByUser(@PathVariable int userId) {
        return repository.getAllByUser(userId);
    }

    @GetMapping("/active")
    public List<Vote> getAllActiveToday(int userId) {
        return repository.getAllActive();
    }

    @GetMapping("/user/{userId}/last")
    public Vote getLastByUser(@PathVariable int userId){
        return repository.getLastByUser(userId);
    }
}
