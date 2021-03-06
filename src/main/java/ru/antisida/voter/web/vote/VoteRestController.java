package ru.antisida.voter.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.antisida.voter.service.VoteService;
import ru.antisida.voter.to.VoteResult;
import ru.antisida.voter.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest/vote")
public class VoteRestController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteTo create(@RequestBody VoteTo voteTo){
        return voteService.create(voteTo, 12000);
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable int id){
//        voteService.delete(id);
//    }

    @GetMapping("/last")
    public VoteTo getLastByDay(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld){
        return voteService.getActiveByUserByDate(12000, ld);
    } //fixme убрать заглушку

    @GetMapping("/last/today")
    public VoteTo getLastToday(){
        return voteService.getLastToday(12000);
    } //fixme убрать заглушку

    @GetMapping("/result")
    public List<VoteResult> getResult(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld){
        if (ld == null) ld = LocalDate.now();
        return voteService.getResult(ld);
    }
}
