package com.aplication.onlinepharmacy.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.ActivityProductDetaillsBinding;
import com.aplication.onlinepharmacy.models.Product;
import com.squareup.picasso.Picasso;

public class product_detaills extends AppCompatActivity {
    ActivityProductDetaillsBinding activityProductDetaillsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         this.activityProductDetaillsBinding = DataBindingUtil.setContentView(this , R.layout.activity_product_detaills);

        Bundle bundle = getIntent().getExtras();
        Product product = new Product(bundle.getInt("id") ,
                0,
                bundle.getString("name") ,
                bundle.getString("description") ,
                bundle.getString("imageUrl") ,
                bundle.getInt("price") ,
                bundle.getBoolean("isAvailable") ,
                bundle.getBoolean("isLike")
        );

        this.activityProductDetaillsBinding.setCProduct(product);
        Picasso.get().load(ApiUrls.PRODUCT_IMAGES + product.getImageUrl()).into(activityProductDetaillsBinding.productImageInScreenDetails);

    }
}