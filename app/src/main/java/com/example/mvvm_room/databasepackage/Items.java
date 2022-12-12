package com.example.mvvm_room.databasepackage;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Items {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "Itemname")
    public String Itemname;

    @ColumnInfo(name = "categoryId")
    public int categoryId;

    @ColumnInfo(name = "completed")
    public boolean completed;
}
