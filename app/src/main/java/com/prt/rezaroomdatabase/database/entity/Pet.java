package com.prt.rezaroomdatabase.database.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pets")
public class Pet {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pet_id")
    private long petId;
    @ColumnInfo(name = "pet_name")
    private String petName;
    @ColumnInfo(name = "pet_type")
    private String type;
    private Date lastVaccinatedDate;

    public Pet() {
    }

    public Pet(long id, String name, String type, Date date) {
        setPetId(id);
        setPetName(name);
        setType(type);
        setLastVaccinatedDate(date);
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastVaccinatedDate() {
        return lastVaccinatedDate;
    }

    public void setLastVaccinatedDate(Date lastVaccinatedDate) {
        this.lastVaccinatedDate = lastVaccinatedDate;
    }
}
