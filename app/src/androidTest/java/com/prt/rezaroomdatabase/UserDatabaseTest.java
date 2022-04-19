package com.prt.rezaroomdatabase;

import android.util.Log;

import com.prt.rezaroomdatabase.database.Database;
import com.prt.rezaroomdatabase.database.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.prt.rezaroomdatabase.factory.UserFactory.*;

@RunWith(AndroidJUnit4.class)
public class UserDatabaseTest {

    public Database database;
    public CompositeDisposable compositeDisposable;
    public final static String TEST_TAG = "TEST_TAG";

    @Before
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext()
                , Database.class)
                .allowMainThreadQueries().build();
        compositeDisposable = new CompositeDisposable();
    }

    @After
    public void end() {
        database.close();
        compositeDisposable.dispose();
    }

    @Test
    public void getUsers() {
        insertUsers(10);

        database.userDao().getUsers().subscribeOn(Schedulers.io()).subscribe(new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) throws Throwable {
                Log.d(TEST_TAG, "onNext: ");
                for (User user : users) {
                    Log.d(TEST_TAG, "       " + user.getUserName());
                }
            }
        });
    }

    private void insertUsers(int count) {
        for (int i = 0; i < count; i++) {
            database.userDao().insertUser(makeUser());
        }
    }
}
