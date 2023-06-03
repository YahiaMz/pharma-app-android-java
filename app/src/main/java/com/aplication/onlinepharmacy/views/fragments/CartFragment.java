package com.aplication.onlinepharmacy.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.adapters.CartAdapter;
import com.aplication.onlinepharmacy.databinding.FragmentCartBinding;
import com.aplication.onlinepharmacy.models.CartItem;
import com.aplication.onlinepharmacy.models.Product;
import com.aplication.onlinepharmacy.viewmodels.CartViewModel;
import com.aplication.onlinepharmacy.views.CheckOutActivity;
import com.aplication.onlinepharmacy.views.LoginActivity;

import java.util.ArrayList;


public class CartFragment extends Fragment implements CartAdapter.IChangeCartItemQuantity {

    private SharedPreferences sharedPreferences;
    private   FragmentCartBinding fragmentCartBinding;
    private   CartAdapter cartAdapter = null;
    private   CartViewModel cartViewModel;
    private  int user_Id ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sharedPreferences = getContext().getSharedPreferences(String.valueOf(R.string.sharedP_file_key), Context.MODE_PRIVATE);
        this.user_Id = sharedPreferences.getInt("user_Id" , -1);
        if(user_Id == -1) {
            startActivity(new Intent(this.getActivity() , LoginActivity.class));
            getActivity().finish();
        }

        this.loadCartItems();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         this.fragmentCartBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_cart , container , false);

         this.fragmentCartBinding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent toCheckout = new Intent(getContext() , CheckOutActivity.class);
                 toCheckout.putExtra("subTotal" , totalPrice());
                 startActivity(toCheckout);
                 loadCartItems();
             }

         });

       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                removeCartItem(cartAdapter.getItems().get(viewHolder.getAdapterPosition()).getId());

                cartAdapter.getItems().remove(viewHolder.getAdapterPosition());
                cartAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                fragmentCartBinding.setTotalPrice(totalPrice());
                if(cartAdapter.getItems().size() == 0) {
                    fragmentCartBinding.setIsTheCartEmpty(true);
                }
            }
        }).attachToRecyclerView(fragmentCartBinding.cartRecyclerView);
         return fragmentCartBinding.getRoot();
    }

    private int totalPrice (){
        int sum = 0 ;
        for ( int x = 0 ; x < cartAdapter.getItems().size() ; x++) {
            sum += cartAdapter.getItems().get(x).getProduct().getPrice() * cartAdapter.getItems().get(x).getQuantity();
        }
        return  sum;
    }
    @Override
    public void changeCartItemQuantity(int iod , int pos) {


       this.cartViewModel.addItemCart(this.user_Id, this.cartAdapter.getItems().get(pos).getProduct().getId() , iod ,this.getContext());
       this.cartAdapter.getItems().get(pos).setQuantity(this.cartAdapter.getItems().get(pos).getQuantity()+iod);
       this.cartAdapter.notifyItemChanged(pos);
       this.fragmentCartBinding.setTotalPrice(this.totalPrice());

    }
    public void loadCartItems( ) {
        this.cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartItemsOfUser(this.user_Id, this.getContext()).observe(this , cartItems -> {
            if(cartAdapter == null) {
                this.cartAdapter = new CartAdapter(cartItems , this);
            }

            this.fragmentCartBinding.setIsTheCartEmpty(cartItems.isEmpty());
            this.fragmentCartBinding.setCartAdapter(this.cartAdapter);
            this.fragmentCartBinding.setTotalPrice(totalPrice());
        });
    }


    public void removeCartItem( int cartItemId) {
        this.cartViewModel.removeCartItem(cartItemId , this.getContext()).observe(this , success -> {
            if(success) {
                Toast.makeText(this.getContext(), "Item removed with success !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}