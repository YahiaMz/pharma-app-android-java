package com.aplication.onlinepharmacy.adapters;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.ProductItemContainerBinding;
import com.aplication.onlinepharmacy.databinding.SearchResultItemContainerBinding;
import com.aplication.onlinepharmacy.models.Product;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductsOfCategoryAdapter extends RecyclerView.Adapter<ProductsOfCategoryAdapter.ProductItemViewHolder>  {

    private ArrayList<Product> productsOfCategory;
    private LayoutInflater layoutInflater;

    public ProductsOfCategoryAdapter(ArrayList<Product> productsOfCategory) {
        this.productsOfCategory = productsOfCategory;
    }

    public ArrayList<Product> getProductsOfCategory() {
        return productsOfCategory;
    }

    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        }
        SearchResultItemContainerBinding productItemContainerBinding = DataBindingUtil.inflate(layoutInflater , R.layout.search_result_item_container , parent , false);
        return new ProductItemViewHolder(productItemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.productsOfCategory.size();
    }


    public class ProductItemViewHolder extends RecyclerView.ViewHolder {

        private SearchResultItemContainerBinding productItemContainerBinding;
        public ProductItemViewHolder(@NonNull SearchResultItemContainerBinding productItemContainerBinding) {
            super(productItemContainerBinding.getRoot());
            this.productItemContainerBinding = productItemContainerBinding;
        }

        public void bind ( int pos ) {
       this.productItemContainerBinding.setProduct(productsOfCategory.get(pos));
            Picasso.get().load(ApiUrls.PRODUCT_IMAGES + productsOfCategory.get(pos).getImageUrl()).into(this.productItemContainerBinding.searchProductImage);
        }

    }



}
