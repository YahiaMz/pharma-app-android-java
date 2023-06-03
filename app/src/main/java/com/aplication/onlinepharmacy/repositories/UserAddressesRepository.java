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
import com.aplication.onlinepharmacy.models.UserAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserAddressesRepository {

    Context mContext;

    public UserAddressesRepository ( Context mContext) {
         this.mContext = mContext;
    }

    public MutableLiveData<ArrayList<UserAddress>> getUserAddresses( int user_Id) {
        MutableLiveData<ArrayList<UserAddress>> mutableLiveDataUserAddresses = new MutableLiveData<>();
        StringRequest fetchUserAddresses = new StringRequest(Request.Method.GET, ApiUrls.USER_ADDRESSES + user_Id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonResponse  = new JSONObject(response);
                    if(jsonResponse.getBoolean("success")) {

                    JSONArray jsonAddresses = jsonResponse.getJSONArray("response");

                    ArrayList<UserAddress> userAddresses = new ArrayList<>();
                    for ( int x = 0 ; x < jsonAddresses.length() ; x ++) {
                    JSONObject cJsonAddress = jsonAddresses.getJSONObject(x);
                    userAddresses.add(new UserAddress(
                            cJsonAddress.getInt("id") ,
                            cJsonAddress.getString("name") ,
                            cJsonAddress.getString("address")
                            ));
                    }

                    mutableLiveDataUserAddresses.setValue(userAddresses);
                    }
                } catch (JSONException e) {
                    Toast.makeText(mContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(this.mContext);
        mRequestQueue.add(fetchUserAddresses);

        return  mutableLiveDataUserAddresses;
    }
    public MutableLiveData<Integer> createUserAddress( int user_Id , String addressName , String addressValue ) {

        Toast.makeText(mContext, user_Id + " " + addressName + " " + addressValue , Toast.LENGTH_SHORT).show();

        MutableLiveData<Integer> success = new MutableLiveData<Integer>(-1);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrls.CREATE_USER_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.getBoolean("success")) {
                        int newAddressId = jsonResponse.getJSONObject("response").getInt("id");
                        success.setValue(newAddressId);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(mContext, "created with success", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> param = new HashMap<>();

                param.put("name" , addressName );
                param.put("address" , addressValue);
                param.put("user_Id" , Integer.toString(user_Id) );

                return  param;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        return  success;

    }




}
