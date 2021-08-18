package ru.antisida.voter.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.repo.datajpa.DataJpaRestaurantRepo;
import ru.antisida.voter.repo.datajpa.DataJpaVoteRepo;
import ru.antisida.voter.to.VoteResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    //fixme проверка check

    private final DataJpaVoteRepo repository;
    private final DataJpaRestaurantRepo restaurantRepo;

    public VoteService(DataJpaVoteRepo repository, DataJpaRestaurantRepo restaurantRepo) {
        this.repository = repository;
        this.restaurantRepo = restaurantRepo;
    }

    //fixme тут нужны проверки на нул
    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

//    @CacheEvict(value = "vote", allEntries = true) //этот метод нужен только в репозитории
//    public void deactivate(int id, int userId){
//        checkNotFoundWithId(repository.deactivate(id, userId), id);
//    }

    @CacheEvict(value = "vote", allEntries = true)
    public Vote create(Vote vote, int userId) { //fixme  тут нужна проверка соответствия userId и vote.userId
        return repository.save(vote, userId);//fixme убрать заглушку
    }

    @CacheEvict(value = "vote", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Cacheable("vote")
    public List<Vote> getAllByUser(int userId) {
        return repository.getAllByUser(userId);
    }

    public List<Vote> getAllActiveByDate(LocalDate ld) {
        return repository.getAllActiveByDate(ld);
    }

    public Vote getLastToday(int userId) {
        return repository.getActiveByUserByDate(userId, LocalDate.now());
    }

    public Vote getActiveByUserByDate(int userId, LocalDate ld) {
        return repository.getActiveByUserByDate(userId, ld);
    }

    @Cacheable("vote")
    public List<Vote> getAll(int userId) {
        return repository.getAll();
    }

    @Transactional
    public List<VoteResult> getResult(LocalDate ld) {

        List<Restaurant> restaurants = restaurantRepo.getAll();
        Map<Integer, List<Vote>> c = repository.getAllActiveByDate(ld)
                .stream()
                .collect(Collectors.groupingBy(Vote::getRestaurantId));

        Map<Integer, Integer> c2 =
                c.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, a -> a.getValue().size()));

        List<VoteResult> voteResults = c2.entrySet()
                .stream()
                .map(a -> {
                    String restaurantName = Objects.requireNonNull(restaurants.stream()
                            .filter(r -> r.getId().intValue() == a.getKey())
                            .findFirst().orElse(null)).getName();
                    return new VoteResult(ld, restaurantName, a.getKey(), a.getValue());
                })
                .collect(Collectors.toList());
        return voteResults;

    }
}
