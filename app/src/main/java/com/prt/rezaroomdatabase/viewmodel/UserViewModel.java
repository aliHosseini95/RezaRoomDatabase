package com.prt.rezaroomdatabase.viewmodel;

import android.content.Context;

import com.prt.rezaroomdatabase.model.database.entity.User;
import com.prt.rezaroomdatabase.model.UserRepository;

import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class UserViewModel extends ViewModel {

    private final UserRepository repository;

    public UserViewModel(Context context) {
        repository = new UserRepository(context);
    }

    public Completable insertUser(User user) {
        return repository.insertUser(user);
    }

    public Flowable<List<User>> getUsers() {
        return repository.getUsers();
    }
}
