package com.example.mvvm_room.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_room.R;
import com.example.mvvm_room.databasepackage.Category;
import com.example.mvvm_room.databasepackage.Items;

import java.util.List;

public class ItemList_Adapter extends RecyclerView.Adapter<ItemList_Adapter.UserHolder> {
    private Context context;
    private List<Items> itemsList;
    private HandleItemCLick handleItemCLick;

    public ItemList_Adapter(Context context, HandleItemCLick handleItemCLick) {
        this.context = context;
        this.handleItemCLick = handleItemCLick;
    }
    public void setItemsList( List<Items> itemsList)
    {
        this.itemsList=itemsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View root= LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false);
      return new UserHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

       Items category=itemsList.get(position);
        holder.tvItemName.setText(itemsList.get(position).Itemname);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleItemCLick.itemclick(itemsList.get(position));
            }
        });
        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleItemCLick.editClick(itemsList.get(position));
            }
        });
        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleItemCLick.removeClick(itemsList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
       if (itemsList==null || itemsList.size()==0)
           return 0;
       else
           return itemsList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        ImageView removeCategory;
        ImageView editCategory;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvCategoryName);
            removeCategory = itemView.findViewById(R.id.removeCategory);
            editCategory =itemView.findViewById(R.id.editCategory);
        }
    }

    public interface HandleItemCLick
    {
        void itemclick(Items items);
        void editClick(Items items);
        void removeClick(Items items);
    }
}
