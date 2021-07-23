package ru.antisida.voter.to;

import org.springframework.stereotype.Component;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.model.Restaurant;

import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class Menu {

    private LocalDate date;

    private Restaurant restaurant;
    private List<Meal> mealList;

    public Menu(Restaurant restaurant, List<Meal> mealList, LocalDate date) {
        this.restaurant = restaurant;
        this.mealList = mealList;
        this.date = date;
    }

    public Menu(List<Meal> mealList) {
        this.restaurant = mealList.get(0).getRestaurant();
        this.mealList = mealList;
        this.date = mealList.get(0).getDate();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return date.equals(menu.date) && restaurant.equals(menu.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, restaurant);
    }
}
