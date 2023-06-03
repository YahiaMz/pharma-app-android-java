package com.aplication.onlinepharmacy.repositories;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.models.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesRepo {
   Context mContext;

    public CategoriesRepo(Context context) {
        this.mContext = context;
    }

    private final String allCategoriesUrl = "http://localhost:5000/categories/";

    public MutableLiveData<ArrayList<Category>> getCategories ( ) {
        MutableLiveData<ArrayList<Category>> categoriesMutableLiveData = new MutableLiveData<>();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrls.CATEGORIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getBoolean("success")) {

                        JSONArray categoriesJsonArray = jsonResponse.getJSONArray("response");

                        ArrayList<Category> categoriesArrayList = new ArrayList<>();

                        for ( int x = 0 ; x< categoriesJsonArray.length() ; x ++) {
                            JSONObject cJsonCategory = (JSONObject) categoriesJsonArray.get(x);
                            categoriesArrayList.add(new Category(cJsonCategory.getInt("id") , cJsonCategory.getString("name") , cJsonCategory.getString("categoryImageUrl")));
                        }

                       // categoriesMutableLiveData = new MutableLiveData<ArrayList<Category>>();
                        categoriesMutableLiveData.setValue(categoriesArrayList);

                    }
                } catch (JSONException e) {
                    Toast.makeText(mContext, "something wrong " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    requestQueue.add(stringRequest);
    return categoriesMutableLiveData;
    }
}
