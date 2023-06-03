package com.aplication.onlinepharmacy.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.aplication.onlinepharmacy.Others.ObjectWraperForBinder;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.adapters.ProductsOfCategoryAdapter;
import com.aplication.onlinepharmacy.databinding.ActivityCategoryProductListBinding;
import com.aplication.onlinepharmacy.models.Product;

import java.util.ArrayList;

public class categoryProductsActivity extends AppCompatActivity {
    ActivityCategoryProductListBinding activityCategoryProductListBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activityCategoryProductListBinding = DataBindingUtil.setContentView(this , R.layout.activity_category_product_list);
        this.onBackClicked();

        String categoryName = getIntent().getExtras().getString("categoryName");
        this.activityCategoryProductListBinding.setCategoryName(categoryName);



        Bundle receivedBundle = getIntent().getExtras();




       ObjectWraperForBinder recievedBinder = (ObjectWraperForBinder)receivedBundle.getBundle("bundle").getBinder("adapter");
       ProductsOfCategoryAdapter productsOfCategoryAdapter =  (ProductsOfCategoryAdapter) recievedBinder.getData();
        if(productsOfCategoryAdapter != null) {
            this.activityCategoryProductListBinding.setIsThereProducts( productsOfCategoryAdapter.getItemCount() != 0 );
            this.activityCategoryProductListBinding.setCategoryProductsAdapter(productsOfCategoryAdapter);

        }

    }

    public  void onBackClicked( ) {
        this.activityCategoryProductListBinding.backImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}