package ru.antisida.voter.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.repo.datajpa.DataJpaRestaurantRepo;
import ru.antisida.voter.repo.datajpa.DataJpaVoteRepo;
import ru.antisida.voter.service.mappers.VoteMapper;
import ru.antisida.voter.to.VoteResult;
import ru.antisida.voter.to.VoteTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final DataJpaVoteRepo repository;
    private final DataJpaRestaurantRepo restaurantRepo;
    private static final VoteMapper voteMapper = VoteMapper.INSTANCE;

    public VoteService(DataJpaVoteRepo repository, DataJpaRestaurantRepo restaurantRepo) {
        this.repository = repository;
        this.restaurantRepo = restaurantRepo;
    }

    public VoteTo get(int id, int userId) {
        return voteMapper.toTo(checkNotFoundWithId(repository.get(id, userId), id));
    }

    @CacheEvict(value = "vote", allEntries = true)
    public VoteTo create(VoteTo voteTo, int userId) {
        Assert.notNull(voteTo, "vote must not be null");
        Vote vote = voteMapper.toEntity(voteTo);
        return voteMapper.toTo(repository.save(vote, userId));
    }

    @CacheEvict(value = "vote", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Cacheable("vote")
    public List<VoteTo> getAllByUser(int userId) {
        return voteMapper.toTos(repository.getAllByUser(userId));
    }

    public List<VoteTo> getAllActiveByDate(LocalDate ld) {
        return voteMapper.toTos(repository.getAllActiveByDate(ld));
    }

    public VoteTo getLastToday(int userId) {
        return voteMapper.toTo(repository.getActiveByUserByDate(userId, LocalDate.now()));
    }

    public VoteTo getActiveByUserByDate(int userId, LocalDate ld) {
        return voteMapper.toTo(repository.getActiveByUserByDate(userId, ld));
    }

    @Cacheable("vote")
    public List<VoteTo> getAll() {
        return voteMapper.toTos(repository.getAll());
    }

    @Transactional
    public List<VoteResult> getResult(LocalDate ld) {

        List<Restaurant> restaurants = restaurantRepo.getAll();
        Map<Integer, List<Vote>> c = repository.getAllActiveByDate(ld)
                .stream()
                .collect(Collectors.groupingBy((Vote vote) -> vote.getRestaurant().getId()));

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
