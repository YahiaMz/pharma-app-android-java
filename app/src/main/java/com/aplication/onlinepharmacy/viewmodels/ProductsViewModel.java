package com.aplication.onlinepharmacy.viewmodels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aplication.onlinepharmacy.models.Product;
import com.aplication.onlinepharmacy.repositories.ProductsRepository;

import java.util.ArrayList;

public class ProductsViewModel extends ViewModel {

    private  MutableLiveData<ArrayList<Product>> productsLiveData = new MutableLiveData<>();
    private ProductsRepository productsRepository = null;


    public MutableLiveData<ArrayList<Product>> getProductsLiveData(Context context) {
        if ( productsRepository == null) {
            this.productsRepository = new ProductsRepository(context);
        }
        if(productsLiveData.getValue() == null) {
            productsLiveData = this.productsRepository.allProducts();
        }
        return productsLiveData;
    }

}
