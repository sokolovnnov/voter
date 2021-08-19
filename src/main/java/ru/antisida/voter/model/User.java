package ru.antisida.voter.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "users")
public class User extends AbstractBaseEntity{

    @NotBlank
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;

    @Email
    @NotBlank
    @Column(name = "e_mail")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @NotNull
    @Column(name = "date_time_reg")
    private LocalDateTime dateTimeReg;

    @OneToMany(fetch = FetchType.LAZY)//todo доделать...
    @JoinColumn(name = "user_id")
    private List<Vote> votes;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 200)
    @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Role> roles;

    public User() {}

    public User(Integer id, String name, String email, String password, boolean enabled, LocalDateTime ld, Role role,
                Role... roles) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.dateTimeReg = ld;
        setRoles(EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.dateTimeReg = LocalDateTime.now();
        setRoles(EnumSet.of(role, roles));
    }

    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.enabled, u.dateTimeReg, u.roles);
    }

    public User(Integer id, String name, String email, String password, boolean enabled, LocalDateTime dateTimeReg, Set<Role> roles) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.dateTimeReg = dateTimeReg;
        setRoles(roles);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateTimeReg() {
        return dateTimeReg;
    }

    public void setDateTimeReg(LocalDateTime dateTimeReg) {
        this.dateTimeReg = dateTimeReg;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
