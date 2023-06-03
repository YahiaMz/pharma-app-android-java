package com.aplication.onlinepharmacy.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.ActivityMainBinding;
import com.aplication.onlinepharmacy.views.fragments.CartFragment;
import com.aplication.onlinepharmacy.views.fragments.HomeFragment;
import com.aplication.onlinepharmacy.views.fragments.LikesFragment;
import com.aplication.onlinepharmacy.views.fragments.myOrdersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding ;
    Fragment selectedFragment = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activityMainBinding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.mainFrameLayout , new HomeFragment());
        mTransaction.commit();
        onBottomNavigationBarClick();
         }


    private  void onBottomNavigationBarClick( ) {
        this.activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();

                switch ( item.getItemId() ) {
                    case R.id.bottom_navigation_bar_home:
                        if (!(selectedFragment instanceof HomeFragment)){
                            selectedFragment = new HomeFragment();
                        mTransaction.replace(R.id.mainFrameLayout, selectedFragment);
                        mTransaction.commit();
                }
                        break;

                    case R.id.bottom_navigation_bar_cart:
                        if( !(selectedFragment instanceof CartFragment) ) {
                            selectedFragment = new CartFragment();
                            mTransaction.replace(R.id.mainFrameLayout, selectedFragment);
                            mTransaction.commit();
                        }
                        break;

                    case R.id.bottom_navigation_bar_orders:
                        selectedFragment = new myOrdersFragment();
                        mTransaction.replace(R.id.mainFrameLayout ,selectedFragment);
                        mTransaction.commit();
                        break;
                    case R.id.bottom_navigation_bar_favorite:
                        if( !(selectedFragment instanceof LikesFragment) ) {
                            selectedFragment = new LikesFragment();
                            mTransaction.replace(R.id.mainFrameLayout, selectedFragment);
                            mTransaction.commit();
                        }
                        break;
                }


                return true;
            }
        });
    }



}