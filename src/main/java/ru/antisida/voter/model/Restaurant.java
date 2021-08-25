package ru.antisida.voter.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "restaurants")
//todo uniqueConstraints
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "name")
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)//todo доделать
    //@JoinColumn(name = "restaurant_id") //fixme ЗАЧЕМ ресторану его еда? Может быть меню? А Может и меню не надо
    private List<Meal> meals;

    public Restaurant(){}

    public Restaurant(@NotNull Integer id,  @NotEmpty String name) {
        super(id);
        this.name = name;
    }

    public Restaurant(Restaurant restaurant){
        this.name = restaurant.name;
        this.id = restaurant.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
