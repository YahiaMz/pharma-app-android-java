package com.aplication.onlinepharmacy.repositories;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.aplication.onlinepharmacy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthRepository {


    private static   AuthRepository instance ;
    Context context;
    private  AuthRepository(Context context) {
        this.context = context;
    }

    public  static AuthRepository getInstance( Context context) {
        if (instance == null) {
            instance = new AuthRepository(context);
        }
        return instance;
    }


    public MutableLiveData<Boolean> login ( String email , String password ){
        MutableLiveData<Boolean> isLoginSuccess = new MutableLiveData<>(false);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, ApiUrls.USER_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.getBoolean("success")) {


                        JSONObject jsonResponseData = jsonResponse.getJSONObject("response");

                        String userName = jsonResponseData.getString("fullName");
                        int user_Id = jsonResponseData.getInt("id");


                        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.sharedP_file_key),Context.MODE_PRIVATE);


                    SharedPreferences.Editor editor =  sharedPreferences.edit();

                    editor.putBoolean("isLogin" , true);
                    editor.putString("user_Name" , userName);
                    editor.putInt("user_Id" , user_Id);

                    editor.apply();
                        Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
                        isLoginSuccess.setValue(true);
                    } else  {
                        Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String > params = new HashMap<>();
                params.put("email" , email);
                params.put("password" , password);

                return params;

            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.add(mStringRequest);

        return  isLoginSuccess;

    }


}
