package com.aplication.onlinepharmacy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.CategoryItemContainerBinding;
import com.aplication.onlinepharmacy.databinding.ProductItemContainerBinding;
import com.aplication.onlinepharmacy.models.Product;
import com.aplication.onlinepharmacy.views.product_detaills;
import com.squareup.picasso.Picasso;


import java.io.Serializable;
import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductItemViewHolder> implements Serializable {

    Context context;

    public interface  IAddToCart{
        public void addToCart( int pos);
    }
    public interface ILike {
        public void like( int pos ) ;
    }

    public interface IProductDetails {
        public void goToProductDetails( int pos ) ;
    }

    private ILike iLike;
    private  IAddToCart iAddToCart;
    private ArrayList<Product> products;
    private LayoutInflater layoutInflater;

    public ProductsAdapter(IAddToCart iAddToCart, ILike ilike ,ArrayList<Product> products , Context context) {
        this.iAddToCart = iAddToCart;
        this.products = products;
        this.iLike = ilike;
        this.context = context;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ProductItemContainerBinding productItemContainerBinding = DataBindingUtil.inflate(layoutInflater , R.layout.product_item_container , parent , false);
        return new ProductItemViewHolder(productItemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }


    public class ProductItemViewHolder extends RecyclerView.ViewHolder {

        private ProductItemContainerBinding productItemContainerBinding;
        public ProductItemViewHolder(@NonNull ProductItemContainerBinding productItemContainerBinding) {
            super(productItemContainerBinding.getRoot());
            this.productItemContainerBinding = productItemContainerBinding;
        }

        public void bind ( int pos ) {
            this.productItemContainerBinding.setCProduct(products.get(pos));
            Picasso.get().load(ApiUrls.PRODUCT_IMAGES + products.get(pos).getImageUrl()).into(this.productItemContainerBinding.productImageOfProductContainer);
            this.productItemContainerBinding.addOneToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iAddToCart.addToCart(pos);
                }
            });
            this.productItemContainerBinding.likeProductImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iLike.like(pos);
                }
            });
            this.productItemContainerBinding.productConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Product product = products.get(pos);

                    Intent toDetailsScreen = new Intent(context , product_detaills.class );
                    toDetailsScreen.putExtra("name" , product.getName() );
                    toDetailsScreen.putExtra("id" , product.getId() );
                    toDetailsScreen.putExtra("isAvailable" , product.isAvailable() );
                    toDetailsScreen.putExtra("price" , product.getPrice() );
                    toDetailsScreen.putExtra("description" , product.getDescription() );
                    toDetailsScreen.putExtra("imageUrl" , product.getImageUrl() );
                    toDetailsScreen.putExtra("isLike" , product.isLike() );

                    context.startActivity(toDetailsScreen);
                }
            });

        }

    }



}
