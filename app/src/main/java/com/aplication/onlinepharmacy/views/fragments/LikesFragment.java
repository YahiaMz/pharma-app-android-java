package com.aplication.onlinepharmacy.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.adapters.LikeAdapter;
import com.aplication.onlinepharmacy.databinding.FragmentLikesBinding;
import com.aplication.onlinepharmacy.databinding.LikedProductItemContainerBinding;
import com.aplication.onlinepharmacy.models.Product;
import com.aplication.onlinepharmacy.viewmodels.LikeViewModel;

import java.util.ArrayList;


public class LikesFragment extends Fragment implements LikeAdapter.IonDislikeBtnClicked , LikeAdapter.IonLikedItemClicked {
FragmentLikesBinding fragmentLikesBinding;
LikeViewModel favoritesViewModel;

ArrayList<Product> likedProducts ;
LikeAdapter likeAdapter;

int user_Id;
SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(favoritesViewModel == null) {
            favoritesViewModel = new ViewModelProvider(this).get(LikeViewModel.class);
        }
        sharedPreferences =getContext().getSharedPreferences( String.valueOf(R.string.sharedP_file_key) , Context.MODE_PRIVATE);
        user_Id = sharedPreferences.getInt("user_Id" , -1);

        if(user_Id == -1) return;

        favoritesViewModel.getLikedProducts(getContext() , user_Id  ).observe(this , likesAl -> {

            Toast.makeText(getContext(), "Size " + likesAl.size(), Toast.LENGTH_SHORT).show();

            if(likedProducts == null) {
                likedProducts = new ArrayList<>(likesAl);
                this.likeAdapter = new LikeAdapter(likesAl , this , this);
                fragmentLikesBinding.setIsThereAnyLikeItem(! likesAl.isEmpty());
                fragmentLikesBinding.setLikesAdapter(likeAdapter);
            }

        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentLikesBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_likes , container , false);
 return fragmentLikesBinding.getRoot();
    }

    @Override
    public void onLikedItemClicked(int pos) {
        Toast.makeText(getContext(), "liked at  " + pos, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDislike(int pos) {
        this.favoritesViewModel.like(getContext() , user_Id , likedProducts.get(pos).getId()).observe(this , result -> {
            if(result == 0) {
                this.likeAdapter.getLikedProductAl().remove(pos);
                this.likedProducts.remove(pos);
                this.likeAdapter.notifyItemRemoved(pos);
                if(likeAdapter.getLikedProductAl().isEmpty()) {
                    fragmentLikesBinding.setIsThereAnyLikeItem(false);
                }
            }
        });
    }
}