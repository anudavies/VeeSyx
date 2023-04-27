package uk.tees.ac.uk.b1197000.veesyx.customerFoodPanel;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import uk.tees.ac.uk.b1197000.veesyx.R;
import uk.tees.ac.uk.b1197000.veesyx.chefFoodPanel.Chef;
import uk.tees.ac.uk.b1197000.veesyx.chefFoodPanel.FoodDetails;
import uk.tees.ac.uk.b1197000.veesyx.chefFoodPanel.chef_postDish;
import uk.tees.ac.uk.b1197000.veesyx.db.CartManagement;
import uk.tees.ac.uk.b1197000.veesyx.entities.ChangeNumberItemsListener;

public class CustomerCartFragmnet extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private CartManagement cartManagement;
    TextView totalPriceTxt, priceTxt, deliveryTxt, totalTxt, emptyTxt,checkOutTxt;
    private double tax;
    private ScrollView scrollView;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,dataa;
    FirebaseAuth Fauth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customercart, null);
        getActivity().setTitle("Cart");
        recyclerViewList= v.findViewById(R.id.cartRecyclerView);
        totalPriceTxt= v.findViewById(R.id.totalPriceText);
        priceTxt= v.findViewById(R.id.taxTxt);
        deliveryTxt= v.findViewById(R.id.deliveryServiceTxt);
        totalTxt= v.findViewById(R.id.totalTxt);
        emptyTxt= v.findViewById(R.id.emptyTxt);
        checkOutTxt = v.findViewById(R.id.checkoutTxtBtn);
        scrollView= v.findViewById(R.id.cartScrollView);
        cartManagement = new CartManagement(getActivity());
        initList();
        CalculateCart();


        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            dataa = firebaseDatabase.getInstance().getReference("User").child(userid);
            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    checkOutTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                //orderFood();

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }
        return v;
    }


    private  void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(cartManagement.getListCarts(),getActivity(),  new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (cartManagement.getListCarts().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }


    private void CalculateCart(){
        double percentTax = 0.02;
        double delivery = 10;

        tax = Math.round((cartManagement.getTotalFee() * percentTax) * 100) / 100;
        double total = Math.round((cartManagement.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(cartManagement.getTotalFee() * 100) / 100;

        totalPriceTxt.setText("£" + itemTotal);
        priceTxt.setText("£" + tax);
        deliveryTxt.setText("£" + delivery);
        totalTxt.setText("£" + total);

    }

}