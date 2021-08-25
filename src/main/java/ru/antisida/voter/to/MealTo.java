package ru.antisida.voter.to;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class MealTo extends BaseTo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private LocalDate date;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Min(0)//todo convert to Rub
    private Integer price;

    @NotBlank
    private String restaurantName;

    @NotNull
    private int restaurantId;

    public MealTo() {
    }

    public MealTo(Integer id, LocalDate date, String name, int price, String restaurantName, int restaurantId) {
        super(id);
        this.date = date;
        this.name = name;
        this.price = price;
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "MealTo{" +
               "id=" + id +
               ", date=" + date +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", restaurantName='" + restaurantName + '\'' +
               ", restaurantId=" + restaurantId +
               '}';
    }
}
