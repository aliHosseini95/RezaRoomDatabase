package com.prt.rezaroomdatabase;

import com.prt.rezaroomdatabase.factory.Factory;
import com.prt.rezaroomdatabase.model.database.Database;
import com.prt.rezaroomdatabase.model.database.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static com.prt.rezaroomdatabase.factory.UserFactory.makeUser;
import static com.prt.rezaroomdatabase.factory.UserFactory.makeUserList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class NewUserDatabaseTest {

    private Database database;

    @Before
    public void setup() {
        database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getContext(), Database.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void endTest() {
        database.close();
    }

    @Test
    public void insertUser() {
        User user = makeUser();
        stubInsertUser(user);
    }

    @Test
    public void updateUser() {
        User user = makeUser();
        stubInsertUser(user);

        user.setUserName(Factory.randomString());
        database.userDao().updateUser(user).test().assertComplete();

        User resultUser = database.userDao().getUserById(user.getUserId()).test().values().get(0);
        assertUsers(resultUser, user);
    }

    @Test
    public void removeUser() {
        User user = makeUser();
        stubInsertUser(user);

        database.userDao().removeUser(user).test().assertComplete();

        assertThat(database.userDao().getUsers().test().awaitDone(1, TimeUnit.SECONDS).values().get(0).size()).isZero();
    }

    @Test
    public void getUsers() {
        List<User> userList = makeUserList(3);
        for (User user : userList) {
            stubInsertUser(user);
        }

        List<User> resultUserList = database.userDao().getUsers()
                .test().awaitDone(1, TimeUnit.SECONDS).values().get(0);

        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o1.getUserId(), o2.getUserId());
            }
        });

        resultUserList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o1.getUserId(), o2.getUserId());
            }
        });

        for (int i = 0; i < userList.size(); i++) {
            assertUsers(userList.get(i), resultUserList.get(i));
        }
    }


//    more methods

    private void stubInsertUser(User user) {
        database.userDao().insertUser(user).test().assertComplete();
    }

    private void assertUsers(User user1, User user2) {
        assertThat(user1.getUserId()).isEqualTo(user2.getUserId());
        assertThat(user1.getUserName()).isEqualTo(user2.getUserName());
        assertThat(user1.getAge()).isEqualTo(user2.getAge());
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
        assertThat(user1.getPhoneNumber()).isEqualTo(user2.getPhoneNumber());
    }
}
