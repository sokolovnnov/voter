package ru.antisida.voter.web;

import org.springframework.stereotype.Controller;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.service.VoteService;

import java.util.List;

@Controller
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

     public Vote get(int id, int userId){
        return voteService.get(id, userId);
    }

    public Vote create(Vote vote, int userId){
        return voteService.create(vote, 12000);
    }

//    public boolean delete(int id, int userId){
//        return voteService.delete(id, userId);
//    }

    public List<Vote> getAllByUser(int userId){
        return voteService.getAllByUser(userId);
    }

    public Vote getLastByUser(int userId){
        return voteService.getLastToday(userId);
    }

    public List<Vote> getAll(int userId){
        return voteService.getAll(userId);
    }
}
