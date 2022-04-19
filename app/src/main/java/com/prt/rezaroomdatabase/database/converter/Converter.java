package com.prt.rezaroomdatabase.database.converter;

import java.util.Date;

import androidx.room.TypeConverter;

public class Converter {

    @TypeConverter
    public long dateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date dateFromLong(long value) {
        return new Date(value);
    }
}
