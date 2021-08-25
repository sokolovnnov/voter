package ru.antisida.voter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "meals")
//todo in topjava: @Table(name = "meals",
//        uniqueConstraints = @UniqueConstraint(name = "user_roles_idx", columnNames = {"USER_ID", "DATE_TIME"}))
//@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class Meal extends AbstractBaseEntity {

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Column(name = "name")
    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @Column(name = "price")
    @Min(0)//todo convert to Rub
    private Integer price;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)//fixme maybe. А нужна ли здесь связь двусторонняя
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(Integer id, LocalDate date, String name, Integer price,
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Meal{" +
               "id=" + id +
               ", date=" + date +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", restaurant=" + restaurant +
               '}';
    }
}
