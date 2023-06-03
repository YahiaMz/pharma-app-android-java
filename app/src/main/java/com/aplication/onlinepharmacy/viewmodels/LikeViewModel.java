package com.aplication.onlinepharmacy.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aplication.onlinepharmacy.models.Product;
import com.aplication.onlinepharmacy.repositories.LikeRepository;

import java.util.ArrayList;

public class LikeViewModel extends ViewModel {

public MutableLiveData<Integer> like (Context context , int user_Id , int product_Id) {
         LikeRepository likeRepository = LikeRepository.getInstance(context);
        return likeRepository.like(user_Id , product_Id);
}

public MutableLiveData<ArrayList<Product>> getLikedProducts ( Context context , int user_Id ){

    LikeRepository likeRepository = LikeRepository.getInstance(context);
//    return  new MutableLiveData<ArrayList<Product>> ( );
   return likeRepository.getLikedProducts(user_Id);
}

}
