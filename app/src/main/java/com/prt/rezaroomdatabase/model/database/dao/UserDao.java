package com.prt.rezaroomdatabase.model.database.dao;

import com.prt.rezaroomdatabase.model.database.entity.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    Flowable<List<User>> getUsers();

    @Query("SELECT * FROM users WHERE user_id == :id")
    Single<User> getUserById(long id);

    @Query("SELECT * FROM users WHERE user_name == :name")
    Single<List<User>> getUsersByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUser(User user);

    @Update
    Completable updateUser(User user);

    @Delete
    Completable removeUser(User user);
}
