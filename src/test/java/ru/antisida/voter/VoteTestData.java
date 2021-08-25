package ru.antisida.voter;

import ru.antisida.voter.model.Vote;
import ru.antisida.voter.to.VoteResult;
import ru.antisida.voter.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class VoteTestData {

    public static final MatcherFactory<Vote> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class,
            "localDateTime");
    public static final MatcherFactory<VoteTo> MATCHER_TO = MatcherFactory.usingIgnoringFieldsComparator(
            VoteTo.class/*, "localDateTime"*/);
    public static final MatcherFactory<VoteResult> VOTE_RESULT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(VoteResult.class);

    public static final int vote01Id = 1000;
    public static final int vote02Id = 1001;
    public static final int vote03Id = 1002;

    public static VoteResult voteResult01 = new VoteResult(LocalDate.of(2021, Month.MAY, 21),
            "Ресторан10_000", 10_000, 1);
    public static VoteResult voteResult02 = new VoteResult(LocalDate.of(2021, Month.MAY, 21),
            "Ресторан10_001", 10_001, 1);


    public static Vote vote00 = new Vote(1005, LocalDateTime.of(2021, Month.MAY, 21, 11, 51, 17),
            RestaurantsTestData.RESTAURANT_2, UserTestData.user, false);
    public static Vote vote01 = new Vote(1000, LocalDateTime.of(2021, Month.MAY, 21, 11, 57, 17),
            RestaurantsTestData.RESTAURANT_2, UserTestData.user, true);
    public static Vote vote02 = new Vote(1001, LocalDateTime.of(2021, Month.MAY, 21, 11, 59, 49),
            RestaurantsTestData.RESTAURANT_1, UserTestData.admin, true);
    public static Vote vote03 = new Vote(1002, LocalDateTime.of(2021, Month.JUNE, 21, 11, 59, 49),
            RestaurantsTestData.RESTAURANT_1, UserTestData.user, true);

    public static final List<Vote> VOTES = List.of(vote01, vote02, vote03);
    public static Vote getDeactivated(){
        Vote deactivated = new Vote(vote01);
        deactivated.setActive(false);
        return deactivated;
    }
    public static Vote getNew(){
        return new Vote(null, LocalDateTime.of(2021, Month.JULY, 22, 11, 57, 17),
                RestaurantsTestData.RESTAURANT_2, UserTestData.user, true);
    }

/*
    INSERT INTO public.votes (id, date_time, user_id, restaurant_id) VALUES (1000, '2021-05-21 11:57:17.000000', 12000, 10001);
    INSERT INTO public.votes (id, date_time, user_id, restaurant_id) VALUES (1001, '2021-05-21 11:59:49.000000', 12000, 10000);
    */
}
