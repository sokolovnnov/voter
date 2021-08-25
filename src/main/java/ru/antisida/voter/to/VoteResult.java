package ru.antisida.voter.to;

import java.time.LocalDate;

public class VoteResult {

    public LocalDate ld;
    public String restaurantName;
    public int restaurantId;
    public int votesCount;

    public VoteResult() {
    }

    public VoteResult(LocalDate ld, String restaurantName, int restaurantId, int votesCount) {
        this.ld = ld;
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.votesCount = votesCount;
    }
}
