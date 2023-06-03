package com.aplication.onlinepharmacy.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsRepository {

    Context mContext;
    public ProductsRepository(Context context) {
        this.mContext = context;
         SharedPreferences  sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.sharedP_file_key) , Context.MODE_PRIVATE);
         this.user_Id = sharedPreferences.getInt("user_Id" , -1);
    }

    int user_Id ;

    public MutableLiveData<ArrayList<Product>> allProducts ( ) {


        MutableLiveData<ArrayList<Product>> productsMutableLiveData = new MutableLiveData<>();
        if(user_Id == -1) return  productsMutableLiveData;

        StringRequest fetchAllProductRequest = new StringRequest(Request.Method.GET, ApiUrls.PRODUCTS_TO + user_Id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            //    Toast.makeText(mContext, "fetch text to " + user_Id, Toast.LENGTH_SHORT).show();

                try {
//                    Toast.makeText(mContext, "all Data : { " + response + " } " , Toast.LENGTH_SHORT).show();
                    JSONObject jsonRESPONSE = new JSONObject(response);
                    if ( jsonRESPONSE.getBoolean("success")) {

                        JSONArray jsonProductsArray = jsonRESPONSE.getJSONArray("response");
                       ArrayList<Product> productsArrayList = new ArrayList<>();
                       for ( int x = 0 ; x < jsonProductsArray.length() ; x ++ ) {

                           JSONObject cJsonProduct = jsonProductsArray.getJSONObject(x);
                           Product cProduct = new Product(cJsonProduct.getInt("id") ,
                                                          cJsonProduct.getInt("category") ,
                                                          cJsonProduct.getString("name"),
                                                          cJsonProduct.getString("description") ,
                                                          cJsonProduct.getString("imageUrl") ,
                                                          cJsonProduct.getInt("price"),
                                                          cJsonProduct.getInt("quantityInStock") != 0,
                                                          cJsonProduct.getInt("isLike") == 1
                                   );
                             productsArrayList.add(cProduct);
                       }
                        productsMutableLiveData.setValue(productsArrayList);
                    }

                } catch (JSONException e) {
                    Toast.makeText(mContext, "In Catch " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(this.mContext);
        mRequestQueue.add(fetchAllProductRequest);

        return  productsMutableLiveData;
    }


}
