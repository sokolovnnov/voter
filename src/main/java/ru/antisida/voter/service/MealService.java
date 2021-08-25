package ru.antisida.voter.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.repo.datajpa.DataJpaMealRepo;
import ru.antisida.voter.service.mappers.MealMapper;
import ru.antisida.voter.to.MealTo;
import ru.antisida.voter.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.antisida.voter.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final DataJpaMealRepo mealRepo;
    private final MealMapper mealMapper;

    public MealService(DataJpaMealRepo repository, MealMapper mealMapper) {
        this.mealMapper = mealMapper;
        this.mealRepo = repository;
    }

    public MealTo get(int id){
        return mealMapper.toTo(checkNotFoundWithId(mealRepo.get(id), id));
    }

    public MealTo create(MealTo mealTo){
        ValidationUtil.checkNew(mealTo);
        Assert.notNull(mealTo, "meal must not be null");
        Meal meal  = mealMapper.toEntity(mealTo);
        Meal newMeal = mealRepo.save(meal);
        return mealMapper.toTo(newMeal);
    }

    public void update(MealTo mealTo, int id){
        Assert.notNull(mealTo, "meal must not be null");
        ValidationUtil.assureIdConsistent(mealTo, id);
        Meal updatedMeal = checkNotFoundWithId(mealRepo.get(id), id);
        mealMapper.updateEntity(mealTo, updatedMeal);
    }

    public void delete(int id){//fixme нужна проверка для админа для всех методов
        checkNotFoundWithId(mealRepo.delete(id), id);
    }

    public List<MealTo> getAll(){
        return mealMapper.toTos(mealRepo.getAll());
    }

    public List<MealTo> getAllByDate(LocalDate ld){
        return mealMapper.toTos(mealRepo.getAllByDate(ld));
    }

    public List<MealTo> getAllByRestaurant(int restaurantId){
        return mealMapper.toTos(mealRepo.getAllByRestaurant(restaurantId));
    }
}
