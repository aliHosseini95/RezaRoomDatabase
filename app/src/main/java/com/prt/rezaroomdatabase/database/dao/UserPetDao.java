package com.prt.rezaroomdatabase.database.dao;

import com.prt.rezaroomdatabase.database.entity.UserPet;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserPetDao {

    @Query("SELECT * FROM users_pets WHERE user_id == :userId")
    Single<List<UserPet>> getUserPetsByUserId(long userId);

    @Query("SELECT * FROM users_pets WHERE pet_id == :petId")
    Single<List<UserPet>> getUserPetsByPetId(long petId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserPet(UserPet userPet);

    @Update
    void updateUserPet(UserPet userPet);

    @Delete
    void removeUserPet(UserPet userPet);
}
