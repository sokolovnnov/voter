package ru.antisida.voter.repo.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaRestaurantRepo {

    private final CrudRestaurantRepo crudRestaurantRepo;

    public DataJpaRestaurantRepo(CrudRestaurantRepo crudRestaurantRepo) {
        this.crudRestaurantRepo = crudRestaurantRepo;
    }

    public Restaurant get(int id){
        return crudRestaurantRepo.findById(id).orElse(null);
    }

    public List<Restaurant> getAll() {
        return crudRestaurantRepo.findAll();
    }

    public List<Restaurant> getActiveByDate(LocalDate ld){
        return crudRestaurantRepo.findActiveByDate(ld);
    }

    @Transactional
    public Restaurant save(Restaurant restaurant){
        return crudRestaurantRepo.save(restaurant);
    }

    public boolean delete(int id){
        return crudRestaurantRepo.delete(id) != 0;
    }
}
