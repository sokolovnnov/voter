package ru.antisida.voter;

import ru.antisida.voter.model.Role;
import ru.antisida.voter.model.User;

import java.time.LocalDateTime;
import java.util.EnumSet;

public class UserTestData {

    public static User user = new User(12000, "user", "user@jj.ru",
            LocalDateTime.of(2021,5, 18, 13, 33, 18 ), EnumSet.of(Role.USER));
    public static User admin = new User(11000, "admin", "admin@jj.ru",
            LocalDateTime.of(2021,5, 18, 13, 34, 1), EnumSet.of(Role.ADMIN));

//(id, name, e_mail, password, date_time_reg) VALUES (12000, 'user', 'user@jj.ru', '1', '2021-05-18 13:33:18.000000');
//   (id, name, e_mail, password, date_time_reg) VALUES (11000, 'admin', 'admin@jj.ru', '1', '2021-05-18 13:34:01.000000');
}
