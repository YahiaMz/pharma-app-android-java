package com.aplication.onlinepharmacy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.LikedProductItemContainerBinding;
import com.aplication.onlinepharmacy.models.Product;
import com.aplication.onlinepharmacy.views.fragments.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeItemViewHolder> {

    private LikedProductItemContainerBinding likedProductItemContainerBinding;

  private LayoutInflater mLayoutInflater;
  private ArrayList<Product> likedProductAl ;


    public ArrayList<Product> getLikedProductAl() {
        return likedProductAl;
    }

    @NonNull
    @Override
    public LikeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mLayoutInflater == null) {
            this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        this.likedProductItemContainerBinding = DataBindingUtil.inflate(mLayoutInflater , R.layout.liked_product_item_container , parent ,false);
        return new LikeItemViewHolder(likedProductItemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.likedProductAl.size();
    }

    public interface  IonLikedItemClicked {
      public void onLikedItemClicked ( int pos);
  }

    public interface  IonDislikeBtnClicked {
        public void onDislike ( int pos);
    }

    IonDislikeBtnClicked ionDislikeBtnClicked;
  private IonLikedItemClicked ionLikedItemClicked;


    public LikeAdapter(ArrayList<Product> likedProductAl, IonLikedItemClicked ionLikedItemClicked , IonDislikeBtnClicked ionDislikeBtnClicked) {
        this.likedProductAl = likedProductAl;
        this.ionLikedItemClicked = ionLikedItemClicked;
        this.ionDislikeBtnClicked = ionDislikeBtnClicked;
    }

    public class LikeItemViewHolder extends RecyclerView.ViewHolder {

        public LikeItemViewHolder(@NonNull LikedProductItemContainerBinding likedProductItemContainerBinding) {
            super(likedProductItemContainerBinding.getRoot());
        }
        public void bind( int pos  ) {

            Product product = likedProductAl.get(pos);
            likedProductItemContainerBinding.setLProduct(product);
            Picasso.get().load(ApiUrls.PRODUCT_IMAGES+product.getImageUrl()).into(likedProductItemContainerBinding.productImageOfLikedProductContainer);

            likedProductItemContainerBinding.removeFromFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ionDislikeBtnClicked.onDislike(pos);
                }
            });
            likedProductItemContainerBinding.likeItemContainerCl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ionLikedItemClicked.onLikedItemClicked(pos);
                }
            });

        }
    }

}
