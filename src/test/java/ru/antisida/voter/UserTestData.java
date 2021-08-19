package ru.antisida.voter;

import ru.antisida.voter.model.Role;
import ru.antisida.voter.model.User;

import java.util.EnumSet;

public class UserTestData {

    public static final MatcherFactory<User> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class,
                    "dateTimeReg", "votes");

    public static final int userId = 12_000;
    public static final int adminId = 11_000;

    public static User user = new User(12000, "user", "user@jj.ru", "password", Role.USER);
    public static User admin = new User(11000, "admin", "admin@jj.ru", "password", Role.ADMIN);

    public static User getNew(){
        return new User(null, "new user", "new@email.com", "newpassword", Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(EnumSet.of(Role.ADMIN));
        return updated;
    }
}
