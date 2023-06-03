package com.aplication.onlinepharmacy.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.adapters.AddressesAddapter;
import com.aplication.onlinepharmacy.viewmodels.UserAddressesViewModel;

public class Add_new_address_Dialog extends AppCompatDialogFragment {
    int user_Id;
    EditText name , value ;
    UserAddressesViewModel userAddressesViewModel;
    SharedPreferences sharedPreferences ;


   public  interface  IonSubmit   {
       public  void onSubmit( int id , String name , String value) ;
   }
    IonSubmit ionSubmit;


    public Add_new_address_Dialog(IonSubmit ionSubmit) {
        this.ionSubmit = ionSubmit;
    }

    @Override
public  Dialog onCreateDialog(Bundle savedInsTse) {

    this.sharedPreferences = getContext().getSharedPreferences(String.valueOf(R.string.sharedP_file_key), Context.MODE_PRIVATE);
     user_Id = sharedPreferences.getInt("user_Id" , -1);


    AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
    LayoutInflater layoutInflater = getLayoutInflater();
    View view  = layoutInflater.inflate( R.layout.add_new_address_dialog , null);
    name = view.findViewById(R.id.addresNameDilog);
    value = view.findViewById(R.id.addressValueDialog);

    this.userAddressesViewModel = new ViewModelProvider(this).get(UserAddressesViewModel.class);

builder.setView(view).setTitle("Add new address").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}).setPositiveButton("submit", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if ( validateInputs() ) addNewAddress();
    }
});



return  builder.create();

}


public  void addNewAddress ( ) {
    this.userAddressesViewModel.createNewUserAdress(this.user_Id , name.getText().toString() , value.getText().toString() , this.getContext()).observe(this , newAddressId -> {
        if(newAddressId != -1) {
            Toast.makeText(getContext(), " address created with success", Toast.LENGTH_SHORT).show();
            ionSubmit.onSubmit(newAddressId , name.getText().toString() , value.getText().toString());
        }
    });
}

public  boolean validateInputs( ) {
    if(this.name.getText().toString().isEmpty()) {
        Toast.makeText(this.getContext(), "address name is required ", Toast.LENGTH_SHORT).show();
        return false;
    }
    if(this.value.getText().toString().isEmpty()) {
        Toast.makeText(this.getContext(), "address is required", Toast.LENGTH_SHORT).show();
        return  false;
    }

    return  true;
}


}
