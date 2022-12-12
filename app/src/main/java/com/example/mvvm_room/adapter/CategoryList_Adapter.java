package com.example.mvvm_room.adapter;

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

import java.util.List;

public class CategoryList_Adapter extends RecyclerView.Adapter<CategoryList_Adapter.UserHolder> {

    private Context context;
    private List<Category> categoryList;
    private HandleCategoryClick clickListener;

    public CategoryList_Adapter(Context context,HandleCategoryClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

   public void setCategoryList(List<Category> categoryList)
   {
       this.categoryList=categoryList;
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


        holder.categoryNAme.setText(this.categoryList.get(position).categoryname);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickListener.itemClick(categoryList.get(position));
            }
        });
        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickListener.editItem(categoryList.get(position));
            }
        });
        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.removeItem(categoryList.get(position));


            }
        });
    }

    @Override
    public int getItemCount() {
       if (categoryList==null|| categoryList.size()==0)
           return 0;
       else
           return categoryList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView categoryNAme;
        ImageView editCategory;
        ImageView removeCategory;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            categoryNAme=itemView.findViewById(R.id.tvCategoryName);
            editCategory=itemView.findViewById(R.id.editCategory);
            removeCategory=itemView.findViewById(R.id.removeCategory);
        }
    }

    public interface HandleCategoryClick
    {
        void itemClick(Category category);
        void removeItem(Category category);
        void editItem(Category category);

    }
}
