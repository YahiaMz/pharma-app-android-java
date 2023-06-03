package com.aplication.onlinepharmacy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.CartItemContainerBinding;
import com.aplication.onlinepharmacy.models.CartItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    private ArrayList<CartItem> items;
    private LayoutInflater layoutInflater;

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public interface IChangeCartItemQuantity {
        // in this function you will pass 1 or -1 to change cart item Quantity
        public  void changeCartItemQuantity ( int iod , int pos);
    }

    public IChangeCartItemQuantity changeCartItemQuantity;
    public CartAdapter(ArrayList<CartItem> items , IChangeCartItemQuantity changeCartItemQuantity) {
        this.items = items;
        this.changeCartItemQuantity = changeCartItemQuantity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if ( layoutInflater == null) {
        this.layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CartItemContainerBinding cartItemContainerBinding = DataBindingUtil.inflate(this.layoutInflater , R.layout.cart_item_container , parent ,false);
        return new CartViewHolder(cartItemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
          holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

      CartItemContainerBinding cartItemContainerBinding;

      public CartViewHolder(@NonNull CartItemContainerBinding cartItemContainerBinding) {
          super(cartItemContainerBinding.getRoot());
          this.cartItemContainerBinding = cartItemContainerBinding;
      }

      public  void  bind( int pos) {
           this.cartItemContainerBinding.setCartItem(items.get(pos));
           Picasso.get().load(ApiUrls.PRODUCT_IMAGES +items.get(pos).getProduct().getImageUrl()).into(this.cartItemContainerBinding.searchProductImage);

           cartItemContainerBinding.increaseQuantityInCartItem.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                     changeCartItemQuantity.changeCartItemQuantity(+1 , pos);
               }
           });
           cartItemContainerBinding.decreaseQuantityInDetailsScreen.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   changeCartItemQuantity.changeCartItemQuantity(-1 , pos);
                   Toast.makeText(itemView.getContext(), " pos " + pos, Toast.LENGTH_SHORT).show();
               }
           });

           this.cartItemContainerBinding.decreaseQuantityInDetailsScreen.setEnabled(items.get(pos).getQuantity() > 1);
           this.cartItemContainerBinding.decreaseQuantityInDetailsScreen.setAlpha( items.get(pos).getQuantity() > 1 ?  1f : 0.3f);

      }


  }


}
