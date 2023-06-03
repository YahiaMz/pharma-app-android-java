package com.aplication.onlinepharmacy.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aplication.onlinepharmacy.Others.ObjectWraperForBinder;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.adapters.CategoriesAdapter;
import com.aplication.onlinepharmacy.adapters.ProductsAdapter;
import com.aplication.onlinepharmacy.adapters.ProductsOfCategoryAdapter;
import com.aplication.onlinepharmacy.databinding.FragmentHomeBinding;
import com.aplication.onlinepharmacy.databinding.ProductItemContainerBinding;
import com.aplication.onlinepharmacy.models.Category;
import com.aplication.onlinepharmacy.models.Product;
import com.aplication.onlinepharmacy.viewmodels.CartViewModel;
import com.aplication.onlinepharmacy.viewmodels.CategoriesViewModel;
import com.aplication.onlinepharmacy.viewmodels.LikeViewModel;
import com.aplication.onlinepharmacy.viewmodels.ProductsViewModel;
import com.aplication.onlinepharmacy.views.LoginActivity;
import com.aplication.onlinepharmacy.views.categoryProductsActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements CategoriesAdapter.OnCategoryItemCLicked , ProductsAdapter.IAddToCart , ProductsAdapter.ILike {

    FragmentHomeBinding fragmentHomeBinding;
    SharedPreferences sharedPreferences;
    int user_Id;
    // categories part
    CategoriesViewModel categoriesViewModel;
    CategoriesAdapter categoriesAdapter;
    ArrayList<Category> categoriesArrayList = null;

    // products part
    ProductsAdapter productsAdapter;
    ProductsViewModel productsViewModel;
    ArrayList<Product> productsArrayList = null;

    // cart part
    CartViewModel cartViewModel = null;

    LikeViewModel likeViewModel;

    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.sharedPreferences = getContext().getSharedPreferences(String.valueOf(R.string.sharedP_file_key), Context.MODE_PRIVATE);
        this.user_Id = sharedPreferences.getInt("user_Id" , -1);
        if(user_Id == -1) {
            startActivity(new Intent(this.getActivity() , LoginActivity.class));
            getActivity().finish();
        }


        //categories
        this.categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        this.categoriesViewModel.loadCategories(this.getContext());
        this.categoriesViewModel.getCategories(this.getContext()).observe(this , ( list ) -> {
            if(categoriesAdapter == null) {
                this.categoriesArrayList = list;
            this.categoriesAdapter = new CategoriesAdapter(list , this);
            }
            this.fragmentHomeBinding.setCategoriesAdapter(this.categoriesAdapter);
        });
        //end with categories

        // products
        this.productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        this.productsViewModel.getProductsLiveData(this.getContext()).observe(this , products -> {
            if(this.productsAdapter == null) {
                this.productsArrayList = products;
                this.productsAdapter = new ProductsAdapter(this  , this, products , getContext());

            }
            this.fragmentHomeBinding.setProductsAdapter(this.productsAdapter);
         });
        // end with products

        // cart
        // ending with cart

        this.likeViewModel = new ViewModelProvider(this).get(LikeViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.fragmentHomeBinding = DataBindingUtil.inflate(inflater ,R.layout.fragment_home,container , false);
        return fragmentHomeBinding.getRoot();


    }

    @Override
    public void onCategoryItemCLicked(int pos) {
        ArrayList<Product> productsOfCategory = new ArrayList<>();
        for ( int x = 0 ; x < this.productsArrayList.size() ; x ++) {
            Product cProduct = this.productsArrayList.get(x);
             if (productsArrayList.get(x).getCategory_Id() == this.categoriesArrayList.get(pos).getId() ) {
                  productsOfCategory.add(cProduct);
             }
        }
        Intent toProductOfCategories = new Intent(this.getContext() , categoryProductsActivity.class);
        toProductOfCategories.putExtra("categoryName" , this.categoriesArrayList.get(pos).getName());
        final Bundle bundle = new Bundle();


        ProductsOfCategoryAdapter productsOfCategoryAdapter = new ProductsOfCategoryAdapter(productsOfCategory);
        bundle.putBinder("adapter", new ObjectWraperForBinder(productsOfCategoryAdapter));
        toProductOfCategories.putExtra("bundle" , bundle);
        startActivity(toProductOfCategories);
    }

    @Override
    public void addToCart(int pos) {
        new ViewModelProvider(this).get(CartViewModel.class).addItemCart(this.user_Id, productsArrayList.get(pos).getId() , 1 , this.getContext()).
                observe(this , isAdded -> {
                   if(isAdded) {
                       Toast.makeText(this.getContext(), "added with success", Toast.LENGTH_SHORT).show();
                   }
                });
    }

    @Override
    public void like(int pos) {

        this.likeViewModel.like(this.getContext() , 11 , productsArrayList.get(pos).getId()).observe(this , result -> {
          if ( result == 0 ) {
//                Toast.makeText(getContext(), "disliked", Toast.LENGTH_SHORT).show();
                productsAdapter.getProducts().get(pos).setLike(false);
                productsArrayList.get(pos).setLike(false);
                productsAdapter.notifyItemChanged(pos);
            } else {
        //        Toast.makeText(getContext(), "liked ", Toast.LENGTH_SHORT).show();
                productsAdapter.getProducts().get(pos).setLike(true);
                productsArrayList.get(pos).setLike(true);
                productsAdapter.notifyItemChanged(pos);
            }
        });

    }
}