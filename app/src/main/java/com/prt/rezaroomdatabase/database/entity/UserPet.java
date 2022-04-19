package com.prt.rezaroomdatabase.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_pets")
public class UserPet {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_pet_id")
    private long userPetId;
    @ColumnInfo(name = "user_id")
    private long userId;
    @ColumnInfo(name = "pet_id")
    private long petId;

    public UserPet() {
    }

    public UserPet(long id, long userId, long petId) {
        setUserPetId(id);
        setUserId(userId);
        setPetId(petId);
    }

    public long getUserPetId() {
        return userPetId;
    }

    public void setUserPetId(long userPetId) {
        this.userPetId = userPetId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }
}
