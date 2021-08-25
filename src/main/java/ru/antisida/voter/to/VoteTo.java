package ru.antisida.voter.to;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class VoteTo extends BaseTo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private LocalDateTime localDateTime;

    private Boolean isActive;

    private Integer restaurantId;

    private String restaurantName;

    private Integer userId;

    private String userName;

    public VoteTo() {
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

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
               "id=" + id +
               ", localDateTime=" + localDateTime +
               ", isActive=" + isActive +
               ", restaurantId=" + restaurantId +
               ", restaurantName='" + restaurantName + '\'' +
               ", userId=" + userId +
               ", userName='" + userName + '\'' +
               '}';
    }
}
