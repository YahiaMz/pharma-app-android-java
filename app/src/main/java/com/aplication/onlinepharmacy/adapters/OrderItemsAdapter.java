package com.aplication.onlinepharmacy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.onlinepharmacy.Others.ApiUrls;
import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.OrderItemContainerBinding;
import com.aplication.onlinepharmacy.models.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderItemsAdapter extends  RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder>{

    private LayoutInflater mLayoutInflater = null ;
    private ArrayList<OrderItem> orderItems;


    public OrderItemsAdapter(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        OrderItemContainerBinding orderItemContainerBinding = DataBindingUtil.inflate(mLayoutInflater , R.layout.order_item_container , parent , false);
        return new OrderItemViewHolder(orderItemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
     holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }


    public class OrderItemViewHolder extends RecyclerView.ViewHolder {

        OrderItemContainerBinding orderItemContainerBinding ;

        public OrderItemViewHolder(@NonNull OrderItemContainerBinding orderItemContainerBinding) {
            super(orderItemContainerBinding.getRoot());
            this.orderItemContainerBinding = orderItemContainerBinding;
        }

        public  void bind(  int pos) {
            Picasso.get().load(ApiUrls.PRODUCT_IMAGES + orderItems.get(pos).getProductImage()).into(this.orderItemContainerBinding.orderItemProductImage);
            this.orderItemContainerBinding.setItemsCount(orderItems.get(pos).getQuantity() +" items");
        }

    }


}


