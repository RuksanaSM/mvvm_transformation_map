package com.example.mvvm_room.viewmodel_package;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvm_room.databasepackage.AppDatabase;
import com.example.mvvm_room.databasepackage.Category;

import java.util.List;

public class MainActivity_ViewModel extends AndroidViewModel {
  private MutableLiveData<List<Category>>  mutablecategorylist;
   private AppDatabase appDatabase;


    public MainActivity_ViewModel(@NonNull Application application) {
        super(application);

        mutablecategorylist=new MutableLiveData<>();
        appDatabase=AppDatabase.getDBInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Category>> getCategorylistObserver()
    {
        return mutablecategorylist;
    }

    public void getAllcategoryList()
    {
        List<Category> categoryList=appDatabase.shoppingListDAO().getAllCategoriesList();
        if (categoryList.size()>0)
        {
            mutablecategorylist.postValue(categoryList);
        }
        else {
            mutablecategorylist.postValue(null);
        }
    }

    public void insertCategory(String catName)
    {
        Category category=new Category();
        category.categoryname=catName;
        appDatabase.shoppingListDAO().insertCategory(category);
        getAllcategoryList();
    }

    public void updateCategoty(Category category)
    {
        appDatabase.shoppingListDAO().updateCategory(category);
        getAllcategoryList();
    }

    public void deleteCategory(Category category)
    {
        appDatabase.shoppingListDAO().deleteCategory(category);
        getAllcategoryList();
    }


}
