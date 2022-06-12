package com.prt.rezaroomdatabase.viewmodel;

import com.prt.rezaroomdatabase.model.database.entity.User;
import com.prt.rezaroomdatabase.model.UserRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class UserViewModel extends ViewModel {

    private final UserRepository repository;

    @Inject
    public UserViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public Completable insertUser(User user) {
        return repository.insertUser(user);
    }

    public Flowable<List<User>> getUsers() {
        return repository.getUsers();
    }
}