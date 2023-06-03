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
import com.aplication.onlinepharmacy.models.CartItem;
import com.aplication.onlinepharmacy.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartRepository {

    Context mContext;
    private CartRepository(Context context ) {
        this.mContext = context;
    }

    private static CartRepository cartRepositoryInstance = null;
    public static   CartRepository getInstance( Context context){
        if(cartRepositoryInstance == null) {
            cartRepositoryInstance = new CartRepository(context);
        }

        return  cartRepositoryInstance;
    }


    public MutableLiveData<ArrayList<CartItem>> getMyItems (int user_Id ) {
        MutableLiveData<ArrayList<CartItem>>  cartItemsOfThisUser = new MutableLiveData<>();

        StringRequest fetchMyItemsRequest = new StringRequest(Request.Method.GET, ApiUrls.CART_OF_USER + user_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    if ( jsonResponse.getBoolean("success")) {

                        ArrayList<CartItem> cartItems = new ArrayList<>();

                        JSONArray cartItemsJSON = jsonResponse.getJSONArray("response");
                        for (int x = 0 ; x < cartItemsJSON.length() ;  x ++) {

                            JSONObject cJsonItem = cartItemsJSON.getJSONObject(x);
                            JSONObject cJsonProduct = cJsonItem.getJSONObject("product");
                            Product cProduct = new Product(cJsonProduct.getInt("id") ,
                                    -1 ,
                                    cJsonProduct.getString("name"),
                                    cJsonProduct.getString("description") ,
                                    cJsonProduct.getString("imageUrl") ,
                                    cJsonProduct.getInt("price"),
                                    cJsonProduct.getInt("quantityInStock") != 0
                            );

                            cartItems.add( new CartItem(cJsonItem.getInt("id") , cJsonItem.getInt("quantity") , cProduct));


                        }

                        cartItemsOfThisUser.setValue(cartItems);

                    }
                } catch (JSONException e) {
                    Toast.makeText(mContext, "in CATCH " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "response " + error.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this.mContext);
        requestQueue.add(fetchMyItemsRequest);
        return  cartItemsOfThisUser;

    }
    public MutableLiveData<Boolean> addItemToCart( int user_Id , int product_Id , int quantity){

        MutableLiveData<Boolean> isAddedWithSuccess = new MutableLiveData<>(false);

        StringRequest addToCartRequest = new StringRequest(Request.Method.POST, ApiUrls.ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                     if (jsonResponse.getBoolean("success")) {
                         isAddedWithSuccess.postValue(true);
                     }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "on error " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String ,String> params = new HashMap<String , String>();

                 params.put("user_Id" , Integer.toString(user_Id));
                 params.put("product_Id" , Integer.toString(product_Id));
                 params.put("quantity" , Integer.toString(quantity));




                return params;
                 }
        };

        RequestQueue mRequestQueue = Volley.newRequestQueue(this.mContext);
        mRequestQueue.add(addToCartRequest);
        return  isAddedWithSuccess;
    }


    public MutableLiveData<Boolean> removeCartItem ( int cartItemId  ) {
        MutableLiveData<Boolean> isRemovedWithSuccess = new MutableLiveData<>(false);
        StringRequest removeCartItemRequest = new StringRequest(Request.Method.DELETE, ApiUrls.REMOVE_CART_ITEM + cartItemId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonRESPONSE = new JSONObject(response);
                    if (jsonRESPONSE.getBoolean("success")) {
                        isRemovedWithSuccess.setValue(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(this.mContext);
        mRequestQueue.add(removeCartItemRequest);

        return isRemovedWithSuccess;
    }
}
