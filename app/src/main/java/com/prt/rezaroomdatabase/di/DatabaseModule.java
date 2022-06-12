package com.prt.rezaroomdatabase.di;

import android.content.Context;

import com.prt.rezaroomdatabase.model.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Singleton
    @Provides
    public Context provideContext(@ApplicationContext Context context) {
        return context;
    }

    @Singleton
    @Provides
    public UserRepository provideUserRepository(@ApplicationContext Context context) {
        return new UserRepository(context);
    }
}
