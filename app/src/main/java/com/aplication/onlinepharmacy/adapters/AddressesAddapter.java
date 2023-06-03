package com.aplication.onlinepharmacy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.AddressItemContainerBinding;
import com.aplication.onlinepharmacy.models.UserAddress;

import java.util.ArrayList;

public class AddressesAddapter  extends  RecyclerView.Adapter<AddressesAddapter.AddressItemVh>{



    public  int currentSelectedAddress = -1;
    private ArrayList<UserAddress> addressesAl ;
    private LayoutInflater mLayoutInflater;
    RecyclerView recyclerView ;

    public AddressesAddapter(ArrayList<UserAddress> addresses , RecyclerView recyclerView) {
        this.addressesAl = addresses;
        this.recyclerView = recyclerView;
    }

    public ArrayList<UserAddress> getAddressesAl() {
        return addressesAl;
    }

    public void setAddressesAl(ArrayList<UserAddress> addressesAl) {
        this.addressesAl = addressesAl;
    }

    @NonNull
    @Override
    public AddressItemVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           if(mLayoutInflater == null) {
            this.mLayoutInflater = LayoutInflater.from(parent.getContext());
           }
           AddressItemContainerBinding addressItemBinding = DataBindingUtil.inflate(this.mLayoutInflater  ,R.layout.address_item_container , parent  , false) ;
      return  new AddressItemVh(addressItemBinding);
     }

    @Override
    public void onBindViewHolder(@NonNull AddressItemVh holder, int position) {
       holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return  this.addressesAl.size();
    }

    public class  AddressItemVh extends RecyclerView.ViewHolder {
        AddressItemContainerBinding addressItemContainerBinding;
        public AddressItemVh(@NonNull AddressItemContainerBinding addressItemContainerBinding) {
            super(addressItemContainerBinding.getRoot());
            this.addressItemContainerBinding = addressItemContainerBinding;
        }

        public  void bind (  int pos ) {
            UserAddress cUserAddress = addressesAl.get(pos);
            this.addressItemContainerBinding.setAddressClass(cUserAddress);
            this.addressItemContainerBinding.selectedAddressIndicator.setVisibility(currentSelectedAddress == pos ?View.VISIBLE : View.GONE);
            this.addressItemContainerBinding.addressItem.setBackgroundResource(currentSelectedAddress == pos ? R.drawable.address_item_selected_background : R.drawable.address_item_background_background);


            this.addressItemContainerBinding.addressItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerView.setBackground(null);
                    currentSelectedAddress = pos;
                    notifyDataSetChanged();
                }
            });
        }

    }


}
