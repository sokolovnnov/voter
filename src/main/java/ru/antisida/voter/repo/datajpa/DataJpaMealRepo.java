package ru.antisida.voter.repo.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Meal;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataJpaMealRepo {

    private final CrudMealRepo crudMealRepo;

    public DataJpaMealRepo(CrudMealRepo crudMealRepo) {
        this.crudMealRepo = crudMealRepo;
    }

    public Meal get(int id) {
        return crudMealRepo.findById(id).orElse(null);
    }

    public List<Meal> getAll() {
        return crudMealRepo.getAllOrdered();
    }

    public List<Meal> getAllByDate(LocalDate ld) {
        return crudMealRepo.findAllByDateOrderByRestaurant(ld);
    }

    public List<Meal> getAllByRestaurant(int restaurantId) {
        return crudMealRepo.getAllByRestaurant(restaurantId);
    }

    public List<Meal> getAllByRestaurantAndByDate(int restaurantId, LocalDate ld){
        return crudMealRepo.getAllByRestaurant(restaurantId).stream().filter(meal -> meal.getDate().equals(ld))
                .collect(Collectors.toList());
    }

    @Transactional
    public Meal save(Meal meal) {
        return crudMealRepo.save(meal);
    }

    @Transactional
    public boolean delete(int id) {
        return crudMealRepo.delete(id) != 0;
    }

}
