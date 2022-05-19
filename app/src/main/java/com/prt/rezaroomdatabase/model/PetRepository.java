package com.prt.rezaroomdatabase.model;

import android.content.Context;

import com.prt.rezaroomdatabase.model.database.Database;
import com.prt.rezaroomdatabase.model.database.dao.PetDao;
import com.prt.rezaroomdatabase.model.database.entity.Pet;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class PetRepository {

    private final PetDao petDao;

    public PetRepository(Context context) {
        petDao = Database.getInstance(context).petDao();
    }

    public void insertPet(Pet pet) {
        petDao.insertPet(pet);
    }

    public Flowable<List<Pet>> getPets() {
        return petDao.getPets();
    }
}
