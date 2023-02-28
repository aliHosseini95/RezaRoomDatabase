package com.prt.rezaroomdatabase.model.database.dao;

import com.prt.rezaroomdatabase.model.database.entity.Pet;

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
    Completable insertPet(Pet pet);

    @Update
    Completable updatePet(Pet pet);

    @Delete
    Completable removePet(Pet pet);
}
