package ru.antisida.voter.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "meals")
//todo in topjava: @Table(name = "meals",
//        uniqueConstraints = @UniqueConstraint(name = "user_roles_idx", columnNames = {"USER_ID", "DATE_TIME"}))
public class Meal extends AbstractBaseEntity {

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "price")
    @Min(0)//todo convert to R
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)//fixme mayby
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Meal() {
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
}
