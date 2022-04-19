package com.prt.rezaroomdatabase.database.dao;

import com.prt.rezaroomdatabase.database.entity.Pet;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PetDao {

    @Query("SELECT * FROM pets")
    Flowable<List<Pet>> getPets();

    @Query("SELECT * FROM pets WHERE pet_id == :id")
    Single<Pet> getPetById(long id);

    @Query("SELECT * FROM pets WHERE pet_name == :name")
    Single<List<Pet>> getPetByName(String name);

    @Query("SELECT * FROM pets WHERE pet_type == :type")
    Single<List<Pet>> getPetByType(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPet(Pet pet);

    @Update
    void updatePet(Pet pet);

    @Delete
    void removePet(Pet pet);
}
