package ru.antisida.voter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "restaurants")
//todo uniqueConstraints
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "name")
    @NotEmpty
    private String name;

//    @OneToMany//todo доделать
//    private List<Meal> meals;
//
//    private Integer voteSum;
//
//    @OneToMany()//todo...
//    private List<Vote> vote;

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
