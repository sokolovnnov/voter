package ru.antisida.voter.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.service.VoteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest/admin/vote")
public class AdminVoteRestController {

    @Autowired
    private VoteService voteService;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        return voteService.get(id, 12000); //fixme убрать заглушку
    }

    @GetMapping("/user/{userId}")
    public List<Vote> getHistoryByUser(@PathVariable int userId) {
        return voteService.getAllByUser(userId);
    }

    @GetMapping("/active")
    public List<Vote> getAllActiveToday() {
        return voteService.getAllActiveByDate(LocalDate.now());
    }

    @GetMapping("/user/{userId}/last")
    public Vote getActiveByUserByDate(@PathVariable int userId,
                              @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld) {
        if (ld == null) ld = LocalDate.now();
        return voteService.getActiveByUserByDate(userId, ld);
    }

}
