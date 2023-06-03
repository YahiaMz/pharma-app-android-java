package com.aplication.onlinepharmacy.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aplication.onlinepharmacy.models.CartItem;
import com.aplication.onlinepharmacy.repositories.CartRepository;

import java.util.ArrayList;

public class CartViewModel extends ViewModel {

    private CartRepository cartRepository = null;
    private MutableLiveData<ArrayList<CartItem>> items = null;
    public MutableLiveData<ArrayList<CartItem>> getCartItemsOfUser(int user_Id  , Context context) {

        this.cartRepository = CartRepository.getInstance(context);
         if(items == null) {
             items = this.cartRepository.getMyItems(user_Id);
         }
         return this.items;
    }

    public  MutableLiveData<Boolean> addItemCart(int user_Id , int product_Id ,int quantity , Context context) {
        this.cartRepository = CartRepository.getInstance(context);
        return this.cartRepository.addItemToCart(user_Id , product_Id , quantity );
    }


    public MutableLiveData<Boolean> removeCartItem ( int cartItemId  ,Context context ) {
        CartRepository cartRepository = CartRepository.getInstance(context);
        return cartRepository.removeCartItem(cartItemId);
    }

}
