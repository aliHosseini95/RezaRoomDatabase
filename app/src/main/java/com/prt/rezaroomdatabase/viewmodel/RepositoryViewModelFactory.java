package com.prt.rezaroomdatabase.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RepositoryViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public RepositoryViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserViewModel(context);
    }
}
