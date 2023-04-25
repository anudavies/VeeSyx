package uk.tees.ac.uk.b1197000.veesyx.customerFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.tees.ac.uk.b1197000.veesyx.R;
import uk.tees.ac.uk.b1197000.veesyx.UpdateDishModel;
import uk.tees.ac.uk.b1197000.veesyx.db.CartManagement;
import uk.tees.ac.uk.b1197000.veesyx.entities.ChangeNumberItemsListener;
import uk.tees.ac.uk.b1197000.veesyx.entities.Food;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private ArrayList<UpdateDishModel> food;
    private CartManagement managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;


    public CartListAdapter(ArrayList<UpdateDishModel> food, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.food = food;
        this.managementCart = new CartManagement(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        position = holder.getAdapterPosition();

        holder.title.setText(food.get(position).getDishes());
        holder.feeEachItem.setText(String.valueOf(food.get(position).getPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round((food.get(position).getNumberInCart() * food.get(position).getPrice()) * 100) / 100));
        holder.num.setText(String.valueOf(food.get(position).getNumberInCart()));

        //TODO if image didnt work try second way 2:32
        Picasso.get().load(food.get(position).getImageURL()).into(holder.pic);
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberFood(food, holder.getAdapterPosition(), new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.minusNumberFood(food, holder.getAdapterPosition(), new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleCartTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
