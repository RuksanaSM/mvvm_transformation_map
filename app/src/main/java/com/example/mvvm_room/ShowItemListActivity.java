package com.example.mvvm_room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mvvm_room.adapter.ItemList_Adapter;
import com.example.mvvm_room.databasepackage.Items;
import com.example.mvvm_room.viewmodel_package.ShowItemList_ViewModel;

import java.util.List;

public class ShowItemListActivity extends AppCompatActivity implements ItemList_Adapter.HandleItemCLick{

    private int category_ID;
    private ItemList_Adapter itemList_adapter;
    private ShowItemList_ViewModel viewModel;
    private RecyclerView recyclerView;
    private Items itemToUpdate=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_list);

        category_ID=getIntent().getIntExtra("categoryId",0);
        String categoryName = getIntent().getStringExtra("categoryName");

        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText addNewItemInput  = findViewById(R.id.addNewItemInput);
        ImageView saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = addNewItemInput.getText().toString();
                if(TextUtils.isEmpty(itemName)) {
                    Toast.makeText(ShowItemListActivity.this, "Enter Item Nane", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(itemToUpdate == null)
                    saveNewItem(itemName);
                else
                    updateNewItem(itemName);
            }

        });
        initRecyclerView();
        initViewModel();
        viewModel.getAllItemsLists(category_ID);
    }

    private void initViewModel() {

        viewModel=new ViewModelProvider(this).get(ShowItemList_ViewModel.class);
        viewModel.getMutableItemsListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if (items==null)
                {
                    recyclerView.setVisibility(View.GONE);
                    findViewById(R.id.noResult).setVisibility(View.VISIBLE);
                }
                else
                {
                    itemList_adapter.setItemsList(items);
                    findViewById(R.id.noResult).setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList_adapter=new ItemList_Adapter(this,this);
        recyclerView.setAdapter(itemList_adapter);
    }

    private void updateNewItem(String itemName) {
        itemToUpdate.Itemname=itemName;
        viewModel.updateItems(itemToUpdate);
        ((EditText) findViewById(R.id.addNewItemInput)).setText("");
        itemToUpdate = null;

    }

    private void saveNewItem(String itemName) {

        Items items=new Items();
        items.Itemname=itemName;
        items.categoryId=category_ID;
        viewModel.insertItems(items);
        ((EditText) findViewById(R.id.addNewItemInput)).setText("");

    }

    @Override
    public void itemclick(Items items) {
        if (items.completed)
        {
            items.completed=false;
        }
        else
        {
            items.completed=true;
        }
        viewModel.updateItems(items);
    }

    @Override
    public void editClick(Items items) {

        this.itemToUpdate = items;
        ((EditText) findViewById(R.id.addNewItemInput)).setText(items.Itemname);

    }

    @Override
    public void removeClick(Items items) {

        viewModel.deleteItems(items);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}