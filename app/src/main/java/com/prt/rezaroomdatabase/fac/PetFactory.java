package com.prt.rezaroomdatabase.fac;

import com.prt.rezaroomdatabase.model.database.entity.Pet;

import java.util.Date;

public class PetFactory {

    public static Pet makePet() {
        return new Pet(
                Factory.randomLong(),
                Factory.randomString(),
                Factory.randomString(),
                new Date()
        );
    }
}
