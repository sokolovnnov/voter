package ru.antisida.voter.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "meals")
//todo in topjava: @Table(name = "meals",
//        uniqueConstraints = @UniqueConstraint(name = "user_roles_idx", columnNames = {"USER_ID", "DATE_TIME"}))
public class Meal extends AbstractBaseEntity {

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "price")
    @Min(0)//todo convert to Rub
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)//fixme maybe. А нужна ли здесь связь двусторонняя
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(Integer id, @NotNull LocalDate date, @NotEmpty String name, @Min(0) Integer price,
                Restaurant restaurant) {
        super(id);
        this.date = date;
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }
}
