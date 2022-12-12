package com.example.mvvm_transformation_map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ModelClass> mutableLiveData = new MutableLiveData<>();

    LiveData<String> liveData = Transformations.map(mutableLiveData, MainViewModel::processData);

    private static String processData(ModelClass modelClass) {

        if (modelClass.fname.isEmpty() || modelClass.lname.isEmpty()) {
            return "Your Full name is invalid";
        } else {
            return "Your Full name is " + modelClass.fname + " " + modelClass.lname;
        }
    }


    public LiveData<String> getLiveData() {

        return liveData;
    }

    public void setLiveData(ModelClass modelClass) {
        mutableLiveData.setValue(modelClass);
    }
}
