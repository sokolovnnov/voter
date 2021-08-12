package ru.antisida.voter.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.service.VoteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vote")
public class VoteRestController {

    @Autowired
    private VoteService voteService;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id, int userId){
        return (repository.get(id, userId), id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vote create(@RequestBody Vote vote, int userId){
        return repository.save(vote, userId);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, int userId){
        (repository.delete(id, userId), id);
    }

    @GetMapping("/last")
    public Vote getLast(){
        return repository.getLastByUser(userId);
    }

    @GetMapping
    public List<Vote> getAll(){
        return repository.getAll();
    }

    @GetMapping("/result")
    public List<VoteResult> getResult(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld){
        if (ld == null) ld = LocalDate.now();
    }
}
