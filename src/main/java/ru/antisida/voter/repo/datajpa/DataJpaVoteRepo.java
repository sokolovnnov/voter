package ru.antisida.voter.repo.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepo {

    private final CrudVoteRepo crudVoteRepo;

    public DataJpaVoteRepo(CrudVoteRepo crudVoteRepo) {
        this.crudVoteRepo = crudVoteRepo;
    }

    @Transactional
    public Vote save(Vote vote, int userId) {
        vote.setUserId(userId);
        Vote lastVote = getActiveByUserByDate(userId, LocalDate.now());
        if (lastVote != null) {
            lastVote.setActive(false);//fixme проверить логику деактивации
            crudVoteRepo.save(lastVote);
        }
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

    public List<Vote> getAllActiveByDate(LocalDate ld) {

//        return crudVoteRepo.findAllActiveByDate(LocalDate.of(2021, Month.MAY, 21).atStartOfDay(),
//                LocalDate.of(2021, Month.MAY, 21).plusDays(1).atStartOfDay());
        return crudVoteRepo.findAllActiveByDate(ld.atStartOfDay(), ld.plusDays(1).atStartOfDay());//fixme
    }

    public List<Vote> getAllByUser(int userId) {
        return crudVoteRepo.findAllByUserId(userId);
    }

    public Vote getActiveByUserByDate(int userId, LocalDate ld) {
        List<Vote> votes = crudVoteRepo.findAllActiveByUserIdAndDate(userId, ld.atStartOfDay(),
                ld.plusDays(1).atStartOfDay());
        if (votes.size() != 0) {
            return votes.get(0);
        }
        else return null;
//        return crudVoteRepo.findAllByUserId(userId).stream()
//                .filter(vote -> vote.getLocalDateTime().toLocalDate() == ld)
//                .max(Comparator.comparing(vote -> vote.getLocalDateTime()))
//                .orElse(null);
    }

    @Transactional
    public Vote deactivate(int id, int userId) {
        Vote vote = crudVoteRepo.getOne(id);
        vote.setActive(false);
        return crudVoteRepo.save(vote);
    }

    public boolean delete(int id) {
        return crudVoteRepo.deleteById(id) != 0;
    }


    public List<Vote> getResult(LocalDate ld) {
        return getAllActiveByDate(ld);

    }
}
