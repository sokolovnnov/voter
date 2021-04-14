package ru.antisida.voter.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @Column(name = "date_time")
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)//todo Fetch
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)//todo Fetch
    @JoinColumn(name = "user_id")
    private User user;
}
//todo All user's votes saves in DB. Last vote calculates in Repository