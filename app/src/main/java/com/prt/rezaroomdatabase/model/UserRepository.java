package com.prt.rezaroomdatabase.model;

import android.content.Context;

import com.prt.rezaroomdatabase.model.database.Database;
import com.prt.rezaroomdatabase.model.database.dao.UserDao;
import com.prt.rezaroomdatabase.model.database.entity.User;
import com.prt.rezaroomdatabase.fac.UserFactory;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class UserRepository {

    private final UserDao userDao;

    public UserRepository(Context context) {
        userDao = Database.getInstance(context).userDao();
    }

    public Completable insertUser(User user) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(UserFactory.makeUser());
            }
        });
    }

    public Flowable<List<User>> getUsers() {
        return userDao.getUsers();
    }
}
