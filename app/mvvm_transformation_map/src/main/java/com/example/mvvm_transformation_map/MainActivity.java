package com.example.mvvm_transformation_map;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.example.mvvm_transformation_map.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

   ActivityMainBinding binding;
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

       // binder= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

      mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);

        setLiveDataObservers();
        setEventListxeners();

    }

    private void setLiveDataObservers() {

        mainViewModel.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                binder.textResult.setText(s);
                binding.textResult.setText(s);
            }
        });
    }

    private void setEventListxeners() {

//        binder.buttonSendData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binder.getViewModel().setLiveData(new ModelClass(
//                        binder.editTextFirstName.getText().toString(),
//                        binder.editTextLastName.getText().toString()
//                ));
//            }
//        });
        binding.buttonSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.setLiveData(new ModelClass(
                        binding.editTextFirstName.getText().toString(),
                        binding.editTextLastName.getText().toString()
                ));
            }
        });
    }
}