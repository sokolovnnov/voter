package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.repo.jpa.*;

import java.time.LocalDate;
import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.*;

@Service
public class MealService {

    private final JpaMealRepo mealRepo;

    public MealService(JpaMealRepo repository) {this.mealRepo = repository;}

    public Meal get(int id, int userId){
        return checkNotFoundWithId(mealRepo.get(id, userId), id);
    }

    public Meal create(Meal meal, int userId){
        return mealRepo.save(meal, userId);
    }

    public void delete(int id, int userId){//fixme нужна проверка для админа для всех методов
        checkNotFoundWithId(mealRepo.delete(id, userId), id);
    }

    public List<Meal> getAll(int userId){
        return mealRepo.getAll(userId);
    }

    public List<Meal> getAllByDate(int userId, LocalDate ld){
        return mealRepo.getAllByDate(userId, ld);
    }

    public List<Meal> getAllByRestaurant(int userId, int restaurantId){
        return mealRepo.getAllByRestaurant(userId, restaurantId);
    }


}
