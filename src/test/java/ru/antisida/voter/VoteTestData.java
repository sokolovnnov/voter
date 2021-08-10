package ru.antisida.voter;

import ru.antisida.voter.model.Vote;

import java.time.LocalDateTime;
import java.time.Month;

public class VoteTestData {

    public static final MatcherFactory<Vote> MATCHER = MatcherFactory.usingIgnoringFieldsComparator();

    public static final int vote01Id = 1000;
    public static final int vote02Id = 1001;
    public static final int vote03Id = 1002;

    public static Vote vote01 = new Vote(1000, LocalDateTime.of(2021, Month.MAY, 21, 11, 57, 17),
            RestaurantsTestData.restaurant_2.id(), UserTestData.user.id(), true);
    public static Vote vote02 = new Vote(1001, LocalDateTime.of(2021, Month.MAY, 21, 11, 59, 49),
            RestaurantsTestData.restaurant_1.id(), UserTestData.admin.id(), true);
    public static Vote vote03 = new Vote(1002, LocalDateTime.of(2021, Month.JUNE, 21, 11, 59, 49),
            RestaurantsTestData.restaurant_1.id(), UserTestData.user.id(), true);

    public static Vote getDeactivated(){
        Vote deactivated = new Vote(vote01);
        deactivated.setActive(false);
        return deactivated;
    }
    public static Vote getNew(){
        return new Vote(null, LocalDateTime.of(2021, Month.JULY, 22, 11, 57, 17),
                RestaurantsTestData.restaurant_2.id(), UserTestData.user.id(), true);
    }

/*
    INSERT INTO public.votes (id, date_time, user_id, restaurant_id) VALUES (1000, '2021-05-21 11:57:17.000000', 12000, 10001);
    INSERT INTO public.votes (id, date_time, user_id, restaurant_id) VALUES (1001, '2021-05-21 11:59:49.000000', 12000, 10000);
    */
}
