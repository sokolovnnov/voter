package ru.antisida.voter.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.to.MealTo;

import java.util.List;

@Component
@Mapper (nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface MealMapper {

    MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

    @Mapping(source = "date", target = "date")
    default Meal toEntity(MealTo mealTo){
        if (mealTo == null){
            return null;
        }

        Meal meal = new Meal();
        meal.setDate(mealTo.getDate());
        meal.setPrice(mealTo.getPrice());
        meal.setName(mealTo.getName());
        meal.setId(mealTo.getId());

        if (mealTo.getRestaurantName() == null){
            meal.setRestaurant(null);
        }else {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(mealTo.getRestaurantName());
            restaurant.setId(mealTo.getRestaurantId());
            meal.setRestaurant(restaurant);
        }
        return meal;
    }

    @Mapping(source = "restaurant.name", target = "restaurantName")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    MealTo toTo(Meal meal);

    List<MealTo> toTos(List<Meal> meals);

    void updateEntity(MealTo source, @MappingTarget Meal target);
}
