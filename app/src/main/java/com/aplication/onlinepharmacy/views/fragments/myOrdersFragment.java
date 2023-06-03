package com.aplication.onlinepharmacy.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.adapters.OrderAdapter;
import com.aplication.onlinepharmacy.databinding.FragmentMyOrdersBinding;
import com.aplication.onlinepharmacy.viewmodels.OrdersViewModel;
import com.aplication.onlinepharmacy.views.LoginActivity;

public class myOrdersFragment extends Fragment {

   private FragmentMyOrdersBinding fragmentMyOrdersBinding;
   private OrdersViewModel ordersViewModel;
   private OrderAdapter orderAdapter;
   int user_Id ;
   SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);

        sharedPreferences = getContext().getSharedPreferences(String.valueOf(R.string.sharedP_file_key), Context.MODE_PRIVATE);


        this.user_Id = sharedPreferences.getInt("user_Id" , -1);
        if ( this.user_Id == -1 ) {
            Intent toLoginActivity =  new Intent(this.getActivity() , LoginActivity.class);
            getActivity().finish();
        }



        this.ordersViewModel.getUserOrders(user_Id , this.getContext()).observe(this , orders -> {

          if(orderAdapter == null) {
              this.orderAdapter = new OrderAdapter(orders);
          }

          this.fragmentMyOrdersBinding.setIsThereAnyOrder (!orders.isEmpty() );
          fragmentMyOrdersBinding.setOrdersAdapter(orderAdapter);

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.fragmentMyOrdersBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_my_orders , container ,false);
        return fragmentMyOrdersBinding.getRoot();
    }
}