package com.aplication.onlinepharmacy.adapters;

import android.security.identity.EphemeralPublicKeyNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.OrderContainerBinding;
import com.aplication.onlinepharmacy.databinding.OrderItemContainerBinding;
import com.aplication.onlinepharmacy.models.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    ArrayList<Order> orders ;
    LayoutInflater mLayoutInflater ;

    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (this.mLayoutInflater == null) {
         this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        OrderContainerBinding orderContainerBinding = DataBindingUtil.inflate(this.mLayoutInflater , R.layout.order_container , parent , false);
        return new OrderViewHolder(orderContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
      holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.orders.size();
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder {

        OrderContainerBinding orderContainerBinding;
        public OrderViewHolder(@NonNull OrderContainerBinding itemView) {
            super(itemView.getRoot());
            orderContainerBinding = itemView;
        }


        public  void bind(  int pos ) {
            OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(orders.get(pos).getItems());
            this.orderContainerBinding.setOrderId(Integer.toHexString(orders.get(pos).getId() ));
            this.orderContainerBinding.setOrderItemsAdapter(orderItemsAdapter);
            this.orderContainerBinding.setAddressName(orders.get(pos).getAddressName());
            this.orderContainerBinding.setTotalPrice(Integer.toString(orders.get(pos).getTotalPrice()) + " Da");
               this.changeTrackUi(pos);
        }


        private   void changeTrackUi( int pos) {
            ArrayList<ImageView> imagePoints = new ArrayList<>();
            imagePoints.add(orderContainerBinding.orderShippedImagePoint);
            imagePoints.add(orderContainerBinding.orderOnTheWayImagePoint);
            imagePoints.add(orderContainerBinding.orderDoneImagePoint);

            ArrayList<ImageView> imagesIcons = new ArrayList<>();
            imagesIcons.add(orderContainerBinding.orderShippedImageIcon);
            imagePoints.add(orderContainerBinding.orderOnTheWayImageIcon);
            imagePoints.add(orderContainerBinding.orderDoneImageIcon);

            ArrayList<View> lines = new ArrayList<>();
            lines.add(orderContainerBinding.view4);
            lines.add(orderContainerBinding.view5);
            lines.add(orderContainerBinding.view6);




            for ( int x = 1 ; x < 4 ; x ++){
                if(x < orders.get(pos).getStatus() ) {

                    imagePoints.get(x-1).setImageResource(R.drawable.greencirle);
                    lines.get(x-1).setBackgroundResource(R.drawable.green_line);
                    lines.get(x-1).setAlpha(1f);
                    imagePoints.get(x-1).setAlpha(1f);


                }else if(x == orders.get(pos).getStatus()) {

                    imagePoints.get(x-1).setImageResource(R.drawable.orangecirle);
                    imagePoints.get(x-1).setAlpha(1f);
                    lines.get(x-1).setBackgroundResource(R.drawable.green_line);


                }else {
                    imagePoints.get(x-1).setImageResource(R.drawable.grey_cirle);
                    lines.get(x-1).setBackgroundResource(R.drawable.grey_line);

                }
            }



        }


    }



}
