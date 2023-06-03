package com.aplication.onlinepharmacy.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LikeRepository {

    private static LikeRepository  likeRepository = null;
    Context context;
    private  LikeRepository(Context context) {
        this.context = context;
    }


    public static   LikeRepository getInstance ( Context context) {
        if (likeRepository == null) {
            likeRepository = new LikeRepository(context);
        }
        return likeRepository;
    }


    // integer for if -1 something wrong else if = 0 unlike , else like
    public MutableLiveData<Integer> like ( int user_Id , int product_Id ) {

        MutableLiveData<Integer> like = new MutableLiveData<>(-1);

        StringRequest likeRequest=new StringRequest(Request.Method.POST, ApiUrls.USER_LIKE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JSONResponse = new JSONObject(response);

                    if(JSONResponse.getBoolean("success")) {

                        String mResponse = JSONResponse.getString("response");

                        if(mResponse.equals("liked")) {
                            like.setValue(1);
                        }else if(mResponse.equals("disliked")){
                            like.setValue(0);
                        }

                    }

                 } catch (JSONException e) {
                    Toast.makeText(context, "Catch : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String , String > params = new HashMap<>();
                   params.put("user_Id" , String.valueOf(user_Id));
                   params.put("product_Id" , String.valueOf(product_Id));

                   return params;

                  }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(likeRequest);
        return like;

    }

    public MutableLiveData<ArrayList<Product>> getLikedProducts(int user_Id) {

        MutableLiveData<ArrayList<Product>> likedProducts = new MutableLiveData<>();
        StringRequest fetchFavoritesProductRequest = new StringRequest(Request.Method.GET, ApiUrls.USER_FAVOURITE_PRODUCTS+user_Id+"/likes" , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
//                    Toast.makeText(mContext, "all Data : { " + response + " } " , Toast.LENGTH_SHORT).show();

                    JSONObject jsonRESPONSE = new JSONObject(response);
                    if ( jsonRESPONSE.getBoolean("success")) {

                        JSONObject jsonObject = jsonRESPONSE.getJSONObject("response");
                        JSONArray jsonProductsArray = jsonObject.getJSONArray("likes");

                        ArrayList<Product> productsArrayList = new ArrayList<>();
                        for ( int x = 0 ; x < jsonProductsArray.length() ; x ++ ) {

                            JSONObject cJsonProduct = jsonProductsArray.getJSONObject(x);
                            Product cProduct = new Product(cJsonProduct.getInt("id") ,
                                    cJsonProduct.getString("name"),
                                    cJsonProduct.getString("description") ,
                                    cJsonProduct.getString("imageUrl") ,
                                    cJsonProduct.getInt("price"),
                                    cJsonProduct.getInt("quantityInStock") != 0
                            );
                            productsArrayList.add(cProduct);
                        }
                        likedProducts.setValue(productsArrayList);
                    }

                } catch (JSONException e) {
                    Toast.makeText(context, "In Catch " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(this.context);
        mRequestQueue.add(fetchFavoritesProductRequest);

        return  likedProducts;
    }


}
