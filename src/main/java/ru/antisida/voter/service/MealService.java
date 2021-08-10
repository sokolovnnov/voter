package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.repo.datajpa.DataJpaMealRepo;

import java.time.LocalDate;
import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final DataJpaMealRepo mealRepo;

    public MealService(DataJpaMealRepo repository) {this.mealRepo = repository;}

    public Meal get(int id){
        return checkNotFoundWithId(mealRepo.get(id), id);
    }

    public Meal create(Meal meal, int userId){
        return mealRepo.save(meal);
    }

    public void delete(int id){//fixme нужна проверка для админа для всех методов
        //checkNotFoundWithId(mealRepo.delete(id), id);
        mealRepo.delete(id);
    }

    public List<Meal> getAll(int userId){
        return mealRepo.getAll();
    }

    public List<Meal> getAllByDate(int userId, LocalDate ld){
        return mealRepo.getAllByDate(ld);
    }

    public List<Meal> getAllByRestaurant(int restaurantId){
        return mealRepo.getAllByRestaurant(restaurantId);
    }


}
