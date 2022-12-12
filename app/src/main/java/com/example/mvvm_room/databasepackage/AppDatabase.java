package com.example.mvvm_room.databasepackage;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Category.class,Items.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ShoppingListDAO shoppingListDAO();
    public static AppDatabase INSTANCE;

    public static AppDatabase getDBInstance(Context context)
    {
        if (INSTANCE==null)
        {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"APPDB")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}