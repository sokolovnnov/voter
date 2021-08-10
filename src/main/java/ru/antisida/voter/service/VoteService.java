package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.repo.datajpa.DataJpaVoteRepo;

import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    //fixme проверка check

    private final DataJpaVoteRepo repository;

    public VoteService(DataJpaVoteRepo repository) {
        this.repository = repository;
    }

    //fixme тут нужны проверки на нул
    public Vote get(int id, int userId){
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void deactivate(int id, int userId){
        checkNotFoundWithId(repository.deactivate(id, userId), id);
    }

    public Vote create(Vote vote, int userId){
        return repository.save(vote, userId);
    }

    public void delete(int id, int userId){
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<Vote> getAllByUser(int userId){
        return repository.getAllByUser(userId);
    }

    public List<Vote> getAllActive(int userId) {
        return repository.getAllActive();
    }

    public Vote getLastByUser(int userId){
        return repository.getLastByUser(userId);
    }

    public List<Vote> getAll(int userId){
        return repository.getAll();
    }

}
