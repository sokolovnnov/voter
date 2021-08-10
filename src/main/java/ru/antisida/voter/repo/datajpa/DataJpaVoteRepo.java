package ru.antisida.voter.repo.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Vote;

import java.util.Comparator;
import java.util.List;

@Repository
public class DataJpaVoteRepo {

    private final CrudVoteRepo crudVoteRepo;

    public DataJpaVoteRepo(CrudVoteRepo crudVoteRepo) {
        this.crudVoteRepo = crudVoteRepo;
    }

    public Vote save(Vote vote, int userId) {
        Vote lastVote = getLastByUser(userId);
        vote.setUserId(userId);
        lastVote.setActive(false);//fixme проверить логику деактивации
        crudVoteRepo.save(lastVote);
        return crudVoteRepo.save(vote);
    }

    public Vote get(int id, int userId) {
        return crudVoteRepo.findById(id)
                .filter(vote -> vote.getUserId() == userId)
                .orElse(null);
    }

    public List<Vote> getAll() {
        return crudVoteRepo.findAll();
    }

    public List<Vote> getAllActive() {
        return crudVoteRepo.findAllByIsActiveTrue();
    }

    public List<Vote> getAllByUser(int userId) {
        return crudVoteRepo.findAllByUserId(userId);
    }

    public Vote getLastByUser(int userId) {
        return crudVoteRepo.findAllByUserId(userId).stream()
                .max(Comparator.comparing(vote -> vote.getLocalDateTime()))
                .orElse(null);
    }

    @Transactional
    public Vote deactivate(int id, int userId) {
        Vote vote = crudVoteRepo.getOne(id);
        vote.setActive(false);
        return crudVoteRepo.save(vote);
    }

    public boolean delete(int id, int userId) {
        return crudVoteRepo.deleteByIdAndUserId(id, userId) != 0;
    }


}
