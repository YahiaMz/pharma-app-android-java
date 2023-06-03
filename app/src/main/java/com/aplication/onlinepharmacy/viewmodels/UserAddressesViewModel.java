package com.aplication.onlinepharmacy.viewmodels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aplication.onlinepharmacy.models.UserAddress;
import com.aplication.onlinepharmacy.repositories.UserAddressesRepository;

import java.util.ArrayList;

public class UserAddressesViewModel extends ViewModel {

    private UserAddressesRepository userAddressesRepository = null;
    private MutableLiveData<ArrayList<UserAddress>> mUserAddresses = null;


    public MutableLiveData<ArrayList<UserAddress>> getmUserAddresses(int user_Id , Context context) {


        if(userAddressesRepository== null) {
         this.userAddressesRepository = new UserAddressesRepository(context);
        }

        if(this.mUserAddresses == null) {
            this.mUserAddresses = this.userAddressesRepository.getUserAddresses(user_Id);
         }
        return  this.mUserAddresses;
    }
    public MutableLiveData<Integer> createNewUserAdress ( int user_Id , String name , String value , Context mContext) {
        if( this.userAddressesRepository == null ) {
          this.userAddressesRepository = new UserAddressesRepository(mContext);
        }
        return  this.userAddressesRepository.createUserAddress(user_Id , name , value );
    }
}
