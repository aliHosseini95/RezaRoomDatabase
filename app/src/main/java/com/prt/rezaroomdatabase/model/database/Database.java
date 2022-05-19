package com.prt.rezaroomdatabase.model.database;

import android.content.Context;

import com.prt.rezaroomdatabase.model.database.converter.Converter;
import com.prt.rezaroomdatabase.model.database.dao.PetDao;
import com.prt.rezaroomdatabase.model.database.dao.UserDao;
import com.prt.rezaroomdatabase.model.database.dao.UserPetDao;
import com.prt.rezaroomdatabase.model.database.entity.Pet;
import com.prt.rezaroomdatabase.model.database.entity.User;
import com.prt.rezaroomdatabase.model.database.entity.UserPet;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@androidx.room.Database(entities = {Pet.class, User.class, UserPet.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class Database extends RoomDatabase {

    public final static String DATABASE_NAME = "pet";

    private static Database database;

    public abstract UserDao userDao();

    public abstract PetDao petDao();

    public abstract UserPetDao userPetDao();

    public static Database getInstance(Context context) {
        if (database == null) {
            synchronized (Database.class) {
                database = Room.databaseBuilder(context, Database.class, DATABASE_NAME)
                        .build();
            }
        }
        return database;
    }
}
