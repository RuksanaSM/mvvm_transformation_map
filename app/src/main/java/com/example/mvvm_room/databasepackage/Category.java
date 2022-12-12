package com.example.mvvm_room.databasepackage;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "CategoryName")
    public String categoryname;
}
