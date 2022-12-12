package com.example.mvvm_room.viewmodel_package;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvm_room.databasepackage.AppDatabase;
import com.example.mvvm_room.databasepackage.Items;

import java.util.List;

public class ShowItemList_ViewModel extends AndroidViewModel {
    private MutableLiveData<List<Items>> mutableItemsList;
    private AppDatabase appDatabase;

    public ShowItemList_ViewModel(@NonNull Application application) {
        super(application);
        mutableItemsList=new MutableLiveData<>();
        appDatabase=AppDatabase.getDBInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Items>> getMutableItemsListObserver()
    {
        return mutableItemsList;
    }

    public void getAllItemsLists(int categoryId)
    {
        List<Items> itemsList=appDatabase.shoppingListDAO().getAllitemsList(categoryId);
        if (itemsList.size()>0)
        {
            mutableItemsList.postValue(itemsList);
        }else
        {
            mutableItemsList.postValue(null);
        }
    }

    public void insertItems(Items items)
    {
        appDatabase.shoppingListDAO().insertItems(items);
        getAllItemsLists(items.categoryId);
    }
    public void updateItems(Items items)
    {
        appDatabase.shoppingListDAO().updateItems(items);
        getAllItemsLists(items.categoryId);
    }
    public  void deleteItems(Items items)
    {
        appDatabase.shoppingListDAO().deleteItems(items);
        getAllItemsLists(items.categoryId);
    }

}
