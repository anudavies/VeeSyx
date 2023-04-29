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

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.tees.ac.uk.b1197000.veesyx.R;
import uk.tees.ac.uk.b1197000.veesyx.UpdateDishModel;
import uk.tees.ac.uk.b1197000.veesyx.entities.ArrayListFood;
import uk.tees.ac.uk.b1197000.veesyx.entities.Food;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder>{
    private Context mcontext;
    private List<UpdateDishModel> updateDishModellist;

    protected ItemListener mListener;
    DatabaseReference databaseReference;

    public CustomerHomeAdapter(Context context , List<UpdateDishModel>updateDishModelslist,ItemListener itemListener){

        this.updateDishModellist = updateDishModelslist;
        this.mcontext = context;

        mListener = itemListener;
    }


    @NonNull
    @Override
    public CustomerHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.customer_menudish,parent,false);
        return new CustomerHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHomeAdapter.ViewHolder holder, int position) {

        final UpdateDishModel updateDishModel = updateDishModellist.get(position);
//        Glide.with(mcontext).load(updateDishModel.getImageURL()).into(holder.imageView);
//        holder.Dishname.setText(updateDishModel.getDishes());
        updateDishModel.getRandomUID();
        updateDishModel.getChefId();
        //holder.Price.setText("£"+updateDishModel.getPrice());
        ((ViewHolder) holder).setData(updateDishModellist.get(position));
    }

    @Override
    public int getItemCount() {
        return updateDishModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ImageView imageView;
        TextView Dishname,Price,addBtn;
        Food item;
        UpdateDishModel items;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.menu_image);
            Dishname = itemView.findViewById(R.id.dishname);
            Price = itemView.findViewById(R.id.dishprice);
            addBtn = itemView.findViewById(R.id.addBtn);

            itemView.setTag(R.id.dishname, Dishname);
            itemView.setTag(R.id.price, Price);
            itemView.setTag(R.id.menu_image, imageView);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, ShowDetailsActivity.class);
                    intent.putExtra("object", items);
                    mcontext.startActivity(intent);
                }
            });
        }
        public void setData(UpdateDishModel item) {
            this.items = item;
            Dishname.setText(item.getDishes());
            Price.setText("£" + item.getPrice());

            Picasso.get().load(item.getImageURL()).into(imageView);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
    public interface ItemListener {
        void onItemClick(Food item);
    }
}
