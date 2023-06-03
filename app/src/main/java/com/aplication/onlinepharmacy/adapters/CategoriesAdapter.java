package com.aplication.onlinepharmacy.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.CategoryItemContainerBinding;
import com.aplication.onlinepharmacy.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryItemViewHolder> {

    private OnCategoryItemCLicked onCategoryItemCLicked ;
    private ArrayList<Category> categories ;
    private LayoutInflater layoutInflater;

    public CategoriesAdapter(ArrayList<Category> categories , OnCategoryItemCLicked onCategoryItemCLicked) {
        this.onCategoryItemCLicked = onCategoryItemCLicked;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CategoryItemContainerBinding categoryItemContainerBinding = DataBindingUtil.inflate(layoutInflater , R.layout.category_item_container , parent , false);
        return new CategoryItemViewHolder(categoryItemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }


    public class CategoryItemViewHolder extends RecyclerView.ViewHolder {

        private CategoryItemContainerBinding categoryItemContainerBinding;
        public CategoryItemViewHolder(@NonNull CategoryItemContainerBinding categoryItemContainerBinding) {
            super(categoryItemContainerBinding.getRoot());
            this.categoryItemContainerBinding = categoryItemContainerBinding;
        }

        public void bind ( int pos ) {
            this.categoryItemContainerBinding.setCategoryName(categories.get(pos).getName());
            this.categoryItemContainerBinding.categoryItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCategoryItemCLicked.onCategoryItemCLicked(pos);
                }
            });
             Picasso.get().load(ApiUrls.CATEGORIES_IMAGES + categories.get(pos).getSvgImageUrl()).into(this.categoryItemContainerBinding.categoryItemContainerImage);
           // Glide.with(this.categoryItemContainerBinding.getRoot().getContext()).load(ApiUrls.CATEGORIES_IMAGES + categories.get(pos).getSvgImageUrl() ).apply(RequestOptions.centerCropTransform()).into(this.categoryItemContainerBinding.categoryItemContainerImage);

        }

    }

    public  interface  OnCategoryItemCLicked {
        public void onCategoryItemCLicked( int pos );
    }


}

