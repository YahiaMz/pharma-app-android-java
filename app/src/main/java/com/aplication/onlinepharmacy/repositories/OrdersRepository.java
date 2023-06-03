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
import com.aplication.onlinepharmacy.Others.OrderCartItemsDataClass;
import com.aplication.onlinepharmacy.models.Order;
import com.aplication.onlinepharmacy.models.OrderItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersRepository {

    Context mContext = null;
    private static   OrdersRepository ordersRepositoryInstance = null;
    private  OrdersRepository(Context context) {
        this.mContext = context;
    }
    public static   OrdersRepository getInstance( Context context) {
        if(ordersRepositoryInstance == null) {
            ordersRepositoryInstance = new OrdersRepository(context);
        }
    return ordersRepositoryInstance;
    }


    public MutableLiveData<Boolean> orderItemsOfUser(int user_Id , OrderCartItemsDataClass orderData) {
        MutableLiveData<Boolean> isOrdersSuccess = new MutableLiveData<>(false);


        StringRequest orderCartItemsRequest = new StringRequest(Request.Method.POST, ApiUrls.ORDER_CART_ITEMS + user_Id, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                     if(jsonResponse.getBoolean("success"))
                     {
                         isOrdersSuccess.setValue(true);
                     }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "In Catch : " +  e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "In Error : " +  error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();

                params.put("address_Id" , Integer.toString(orderData.getAddress_Id()));
                params.put("phoneNumber" , orderData.getPhoneNumber());
                params.put("paymentMethod" , orderData.getPaymentMethod());
                params.put("shippingPrice" , Integer.toString(orderData.getShippingPrice()));

                return  params;

                 }
        };


        RequestQueue mRequestQueue = Volley.newRequestQueue(this.mContext);
        mRequestQueue.add(orderCartItemsRequest);
        return isOrdersSuccess;
    }



public  MutableLiveData<ArrayList<Order>> userOrders( int user_Id ) {
        MutableLiveData<ArrayList<Order>> myOrders = new MutableLiveData<>();


        StringRequest getUserOrderRequest = new StringRequest(Request.Method.GET, ApiUrls.ORDERS_OF_USER + user_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.getBoolean("success")) {
                        JSONArray jsonORDERS = jsonResponse.getJSONArray("response");
                        ArrayList<Order> ordersAL = new ArrayList<>();

                        for ( int o = 0 ; o < jsonORDERS.length() ; o ++) {

                            JSONObject cJSON_Order = jsonORDERS.getJSONObject(o);


                        // getting order items
                        JSONArray jsonItems = cJSON_Order.getJSONArray("items");
                        ArrayList<OrderItem> orderItems = new ArrayList<>();
                        for (int x = 0 ; x < jsonItems.length() ; x ++) {
                            JSONObject cJsonOrderItem = jsonItems.getJSONObject(x);

                            orderItems.add(new OrderItem(cJsonOrderItem.getInt("id") ,
                                    cJsonOrderItem.getString("imageUrl") ,
                                    cJsonOrderItem.getInt("orderItemQuantity")
                                    ));
                        }
                        //

                            ordersAL.add(new Order(
                                    cJSON_Order.getInt("id") ,
                                    orderItems ,
                                    cJSON_Order.getJSONObject("userAddress").getString("name"),
                                    cJSON_Order.getInt("status") ,
                                    cJSON_Order.getBoolean("isActive") ,
                                    cJSON_Order.getInt("totalPrice")
                            ));


                        }

                   myOrders.setValue(ordersAL);

                    }

                } catch (JSONException e) {
                    Toast.makeText(mContext, "In Catch : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "In Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this.mContext);
        requestQueue.add(getUserOrderRequest);
        return  myOrders;
}



}
