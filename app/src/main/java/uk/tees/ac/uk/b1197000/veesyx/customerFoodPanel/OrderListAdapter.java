package uk.tees.ac.uk.b1197000.veesyx.customerFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.tees.ac.uk.b1197000.veesyx.OrdersModel;
import uk.tees.ac.uk.b1197000.veesyx.R;
import uk.tees.ac.uk.b1197000.veesyx.UpdateDishModel;
import uk.tees.ac.uk.b1197000.veesyx.entities.Food;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{
    private Context mcontext;
    private List<OrdersModel> orderList;
    protected ItemListener mListener;
    public OrderListAdapter(Context context , List<OrdersModel> orderList, ItemListener itemListener){
        this.orderList = orderList;
        this.mcontext = context;
        this.mListener = itemListener;

    }

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.viewholder_orderlist,parent,false);
        return new OrderListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ViewHolder holder, int position) {
        final OrdersModel ordersModel = orderList.get(position);
        holder.txtDate.setText(ordersModel.getDate() + " " + ordersModel.getTime());
        holder.txtTotalAmount.setText("£"+ ordersModel.getTotalPrice());
        holder.txtStatus.setText(ordersModel.getStatus());
        holder.txtOrderId.setText(ordersModel.getOrderId().substring(0,8));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView txtDate,txtOrderId,txtStatus,txtTotalAmount;
        OrdersModel item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.titleOrderNo);
            txtOrderId = itemView.findViewById(R.id.orderId);
            txtStatus = itemView.findViewById(R.id.numberItemTxt);
            txtTotalAmount =itemView.findViewById(R.id.totalPrice);

            itemView.setTag(R.id.titleOrderNo,txtDate);
            itemView.setTag(R.id.orderId, txtOrderId);
            itemView.setTag(R.id.numberItemTxt, txtStatus);
            itemView.setTag(R.id.totalPrice,txtTotalAmount);

        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
    public interface ItemListener {
        void onItemClick(OrdersModel item);
    }
}
