package ru.antisida.voter.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "restaurants")
//todo uniqueConstraints
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "name")
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)//todo доделать
    //@JoinColumn(name = "restaurant_id") //fixme ЗАЧЕМ ресторану его еда?
    private List<Meal> meals;
//
//    private Integer voteSum;//todo сумма сегодняшних голосов она тут не нужна
//
//    @OneToMany(fetch = FetchType.LAZY)//todo... ЗАЧЕМ РЕСТОРАНУ ЕГО ГОЛОСА???
//    @JoinColumn(name = "restaurant_id")
//    private List<Vote> vote;

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
}
