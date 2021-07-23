package ru.antisida.voter.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @Column(name = "date_time")
    private LocalDateTime localDateTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "restaurant_id")
    private Integer restaurantId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "restaurant_id")
//    private Restaurant restaurant;

    @Column(name = "user_id")
    private Integer userId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    private User user;


    public Vote() {
    }

    public Vote(Integer id, LocalDateTime localDateTime, int restaurantId, int userId, boolean isActive) {
        super(id);
        this.localDateTime = localDateTime;
        this.isActive = isActive;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    public Vote(Vote vote){
        this(vote.id, vote.localDateTime, vote.restaurantId, vote.userId, vote.isActive);
    }

//    public Vote(Integer id, LocalDateTime localDateTime, Restaurant restaurant, User user) {
//        super(id);
//        this.localDateTime = localDateTime;
//        this.isActive = true;
//        this.restaurant = restaurant;
//        this.user = user;
//    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public int getUserId() {
        return userId;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
//todo All user's votes saves in DB. Last vote calculates in Repository