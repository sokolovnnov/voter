package ru.antisida.voter.util;

import ru.antisida.voter.model.Meal;
import ru.antisida.voter.to.Menu;

import java.util.List;
import java.util.stream.Collectors;

public class MenuUtil {

    public static Menu toMenu(List<Meal> meals){
        int sizeDate = meals.stream().collect(Collectors.groupingBy(meal -> meal.getDate())).keySet().size();
        int sizeRest = meals.stream().collect(Collectors.groupingBy(meal -> meal.getRestaurant())).keySet().size();
        if ((sizeDate == 1) & (sizeRest == 1)){
            return new Menu(meals);
        }
        else {
            throw new IllegalArgumentException("Data for creating one menu is incorrect: date - " + sizeDate + ", " +
                                               "rest - " + sizeRest );
        }
    }

    public static List<Menu> toRestaurantListMenu(List<Meal> meals){
        if (meals.stream().collect(Collectors.groupingBy(meal -> meal.getRestaurant())).keySet().size() == 1){
            return meals.stream().collect(Collectors.groupingBy(meal -> meal.getDate()))
                    .values().stream()
                    .map(mealList -> {return new Menu(mealList);})
                    .collect(Collectors.toList())
            ;
        }
        else {
            throw new IllegalArgumentException("To more than one restaurant");
        }
    }

    public static List<Menu> toDateListMenu(List<Meal> meals){
        if (meals.stream().collect(Collectors.groupingBy(meal -> meal.getDate())).keySet().size() == 1){
            return meals.stream().collect(Collectors.groupingBy(meal -> meal.getRestaurant()))
                    .values().stream()
                    .map(mealList -> {
                        return new Menu(mealList);
                    })
                    .collect(Collectors.toList());
        }
        else {
            throw new IllegalArgumentException("To more then one date");
        }
    }
}
