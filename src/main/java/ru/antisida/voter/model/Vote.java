package ru.antisida.voter.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime localDateTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Vote() {
    }

    public Vote(Integer id, LocalDateTime localDateTime, Restaurant restaurant, User user, boolean isActive) {
        super(id);
        this.localDateTime = localDateTime;
        this.restaurant = restaurant;
        this.user = user;
        this.isActive = isActive;
    }

    public Vote(Vote vote){
        this(vote.id, vote.localDateTime, vote.restaurant, vote.user, vote.isActive);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
               "id=" + id +
               ", localDateTime=" + localDateTime +
               ", isActive=" + isActive +
               ", restaurant=" + restaurant +
               ", user=" + user +
               '}';
    }
}
