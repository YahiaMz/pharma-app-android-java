package com.aplication.onlinepharmacy.viewmodels;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.aplication.onlinepharmacy.Others.OrderCartItemsDataClass;
import com.aplication.onlinepharmacy.models.Order;
import com.aplication.onlinepharmacy.repositories.OrdersRepository;

import java.util.ArrayList;

public class OrdersViewModel extends ViewModel {

 public MutableLiveData<Boolean> orderItemsInCart(int user_Id , OrderCartItemsDataClass orderData , Context context) {
  OrdersRepository mOrdersRepository = OrdersRepository.getInstance(context);
  return  mOrdersRepository.orderItemsOfUser(user_Id , orderData );
 }


 public MutableLiveData<ArrayList<Order>> getUserOrders ( int user_Id , Context context){
   OrdersRepository ordersRepository = OrdersRepository.getInstance(context);
   return ordersRepository.userOrders(user_Id);
 }

}
