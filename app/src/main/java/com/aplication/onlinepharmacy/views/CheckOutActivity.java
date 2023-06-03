package com.aplication.onlinepharmacy.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aplication.onlinepharmacy.Others.OrderCartItemsDataClass;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.adapters.AddressesAddapter;
import com.aplication.onlinepharmacy.databinding.ActivityCheckOutBinding;
import com.aplication.onlinepharmacy.models.UserAddress;
import com.aplication.onlinepharmacy.viewmodels.OrdersViewModel;
import com.aplication.onlinepharmacy.viewmodels.UserAddressesViewModel;
import com.aplication.onlinepharmacy.views.fragments.Add_new_address_Dialog;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CheckOutActivity extends AppCompatActivity implements Add_new_address_Dialog.IonSubmit {
    ActivityCheckOutBinding activityCheckOutBinding;
    UserAddressesViewModel viewModel ;
    AddressesAddapter addressesAddapter = null;
    ArrayList<UserAddress> addresses = new ArrayList<>();
    SharedPreferences sharedPreferences;
    int user_Id ;

    OrdersViewModel ordersViewModel ;


    final int  SHIPPPING_PRICE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        this.activityCheckOutBinding = DataBindingUtil.setContentView(this , R.layout.activity_check_out);

        // getting coming data
        Bundle commingData = getIntent().getExtras();
        int subTotal = commingData.getInt("subTotal");
        this.activityCheckOutBinding.setSubTotal(subTotal);
        // end getting coming data

        this.activityCheckOutBinding.setShipping(0);


        sharedPreferences = getApplicationContext().getSharedPreferences(String.valueOf(R.string.sharedP_file_key), MODE_PRIVATE);
        this.user_Id = sharedPreferences.getInt("user_Id" , -1);
        if ( this.user_Id == -1 ) {
            Intent toLoginActivity =  new Intent(this , LoginActivity.class);
            finish();
        }

        this.viewModel = new ViewModelProvider(this).get(UserAddressesViewModel.class);
        this.viewModel.getmUserAddresses(user_Id , this).observe(this , userAddresses -> {
            if(this.addressesAddapter == null) {
             this.addressesAddapter = new AddressesAddapter(userAddresses , activityCheckOutBinding.addressesRecyclerView);
             this.addresses = userAddresses;
            }
            this.activityCheckOutBinding.setAddressesAddapater(this.addressesAddapter);
            this.activityCheckOutBinding.setIsThereAnyAddress(!userAddresses.isEmpty());

        });
        this.ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);

        this.activityCheckOutBinding.PlaceOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean doOrder = true;
              if(! validatePhoneNumber()) {
                  activityCheckOutBinding.phoneNumberEditText.setError("please enter valid phone number" );
                   doOrder = false;
              }
              if(addressesAddapter.currentSelectedAddress == -1) {
                  if(addresses.isEmpty()) {
                       activityCheckOutBinding.addNewAddress.setBackgroundResource(R.drawable.add_new_address_with_force_background);
                  } else
                  activityCheckOutBinding.addressesRecyclerView.setBackgroundResource(R.drawable.non_selected_address_background);
               doOrder = false;
              }

              if(doOrder) {
                  orderCartItems();
              }

            }
        });


        this.activityCheckOutBinding.phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               validatePhoneNumber();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        this.activityCheckOutBinding.addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddNewAddressDialog();
            }
        });

    }

public  boolean validatePhoneNumber ( ) {
        boolean isMatch = Pattern.compile("0[5,6,7][0-9]{8}").matcher(this.activityCheckOutBinding.phoneNumberEditText.getText().toString()).matches();
        this.activityCheckOutBinding.phoneNumberEditText.setBackgroundResource(isMatch ? R.drawable.search_drugs_edit_text_background : R.drawable.wrong_phonenumber_background);
      return isMatch;
    }


    private  void orderCartItems ( ) {
        String orderPhoneNumber = activityCheckOutBinding.phoneNumberEditText.getText().toString();
        String paymentMethod = "cash";

        int selectedAddressId = addresses.get(this.addressesAddapter.currentSelectedAddress).getId();
        OrderCartItemsDataClass orderCartItemsDataClass = new OrderCartItemsDataClass( selectedAddressId , paymentMethod , orderPhoneNumber , SHIPPPING_PRICE  );

        this.ordersViewModel.orderItemsInCart( this.user_Id , orderCartItemsDataClass ,this  ).observe(this , success-> {

            if(success) {
                goToSuccessOrderActivity();
            }

        });
    }

    public  void goToSuccessOrderActivity( ) {
        Intent toOrderSuccessfulActivity = new Intent(this , OrderPalcedWithSuccess.class);
        startActivity(toOrderSuccessfulActivity);
        finish();
    }

    public  void openAddNewAddressDialog( ) {
        Add_new_address_Dialog add_new_address_dialog = new Add_new_address_Dialog(this);
        add_new_address_dialog.show(getSupportFragmentManager() , "Address");
    }

    @Override
    public void onSubmit(int id, String name, String value) {


        Toast.makeText(this, "user_Id : " + id + " " + name + " " + value , Toast.LENGTH_SHORT).show();

        addresses.add(0 ,new UserAddress(id , name , value));
        addressesAddapter.getAddressesAl().clear();
        addressesAddapter.setAddressesAl(addresses);

        addressesAddapter.notifyDataSetChanged();
    }
}