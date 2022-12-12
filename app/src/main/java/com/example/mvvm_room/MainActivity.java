package com.example.mvvm_room;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm_room.adapter.CategoryList_Adapter;
import com.example.mvvm_room.databasepackage.Category;
import com.example.mvvm_room.viewmodel_package.MainActivity_ViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryList_Adapter.HandleCategoryClick {

    private RecyclerView reyclerview;
    private MainActivity_ViewModel viewModel;
    private Category editcategory;
    private CategoryList_Adapter categoryList_adapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Shopping wishlist");
        textView=findViewById(R.id.noResult);
        reyclerview=findViewById(R.id.recyclerView);
        ImageView addNew = findViewById(R.id.addNewCategoryImageView);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAddcategoryDailog(false);

            }
        });

        initViewModel();
        initRecyclervew();
        viewModel.getAllcategoryList();
    }

    private void initRecyclervew() {
        reyclerview.setLayoutManager(new LinearLayoutManager(this));
        categoryList_adapter=new CategoryList_Adapter(this,this);
        reyclerview.setAdapter(categoryList_adapter);
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(this).get(MainActivity_ViewModel.class);
        viewModel.getCategorylistObserver().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories==null)
                {
                    textView.setVisibility(View.VISIBLE);
                    reyclerview.setVisibility(View.GONE);
                }else
                {
                    //show in the recyclerview
                    categoryList_adapter.setCategoryList(categories);
                    reyclerview.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                }
            }
        });
    }


    private void showAddcategoryDailog(boolean isforEdit) {
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        View dialogView=getLayoutInflater().inflate(R.layout.add_category_layout,null);
        EditText enterCategoryInput = dialogView.findViewById(R.id.enterCategoryInput);
        TextView createButton = dialogView.findViewById(R.id.createButton);
        TextView cancelButton = dialogView.findViewById(R.id.cancelButton);

        if (isforEdit)
        {
            createButton.setText("Update");
            enterCategoryInput.setText(editcategory.categoryname);

        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = enterCategoryInput.getText().toString();
                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this, "Enter category name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isforEdit){
                    editcategory.categoryname = name;
                    viewModel.updateCategoty(editcategory);
                } else {
                    //here we need to call view model.
                    viewModel.insertCategory(name);
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);


        alertDialog.show();
    }

    @Override
    public void itemClick(Category category) {

        Intent intent=new Intent(MainActivity.this,ShowItemListActivity.class);
        intent.putExtra("categoryId",category.uid);
        intent.putExtra("categoryName",category.categoryname);

        startActivity(intent);
    }

    @Override
    public void removeItem(Category category) {

        viewModel.deleteCategory(category);
    }

    @Override
    public void editItem(Category category) {

        this.editcategory=category;
        showAddcategoryDailog(true);
    }
}



