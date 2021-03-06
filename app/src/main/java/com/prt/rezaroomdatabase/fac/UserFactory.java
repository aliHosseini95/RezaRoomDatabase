package com.prt.rezaroomdatabase.fac;

import com.prt.rezaroomdatabase.model.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    public static User makeUser() {
        return new User(
                Factory.randomLong(),
                Factory.randomString(),
                Factory.randomString(),
                Factory.randomInt(),
                Factory.randomString()
        );
    }

    public static List<User> makeUserList(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(makeUser());
        }
        return users;
    }
}
