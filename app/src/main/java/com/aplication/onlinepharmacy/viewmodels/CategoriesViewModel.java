package com.aplication.onlinepharmacy.viewmodels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aplication.onlinepharmacy.models.Category;
import com.aplication.onlinepharmacy.repositories.CategoriesRepo;

import java.util.ArrayList;

public class CategoriesViewModel extends ViewModel {
     private CategoriesRepo categoriesRepo = null;
     private MutableLiveData<ArrayList<Category>> categories = null;

     public MutableLiveData<ArrayList<Category>> getCategories(Context context) {
           loadCategories(context);
           return categories;
     }
     public void loadCategories (Context context) {
          if (categoriesRepo == null) {
               this.categoriesRepo = new CategoriesRepo(context);
          }
          if(categories == null) {
               this.categories = this.categoriesRepo.getCategories();


          }
     }

}
