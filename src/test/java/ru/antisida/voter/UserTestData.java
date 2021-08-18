package ru.antisida.voter;

import ru.antisida.voter.model.Role;
import ru.antisida.voter.model.User;

public class UserTestData {

    public static final int userId = 12_000;
    public static final int adminId = 11_000;

    public static User user = new User(12000, "user", "user@jj.ru", "password",
            //LocalDateTime.of(2021,5, 18, 13, 33, 18 ),
            //true,
            Role.USER);
    public static User admin = new User(11000, "admin", "admin@jj.ru", "password",
         //   LocalDateTime.of(2021,5, 18, 13, 34, 1),
            //true,
            Role.ADMIN);

//(id, name, e_mail, password, date_time_reg) VALUES (12000, 'user', 'user@jj.ru', '1', '2021-05-18 13:33:18.000000');
//   (id, name, e_mail, password, date_time_reg) VALUES (11000, 'admin', 'admin@jj.ru', '1', '2021-05-18 13:34:01.000000');
}
