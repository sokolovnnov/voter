package ru.antisida.voter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity{

    @Column(name = "name")
    private String name;

    @OneToMany//todo доделать...
    private List<Vote> votes;
}
