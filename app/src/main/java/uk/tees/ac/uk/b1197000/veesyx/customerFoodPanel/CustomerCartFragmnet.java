package uk.tees.ac.uk.b1197000.veesyx.customerFoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uk.tees.ac.uk.b1197000.veesyx.R;
import uk.tees.ac.uk.b1197000.veesyx.db.CartManagement;
import uk.tees.ac.uk.b1197000.veesyx.entities.ChangeNumberItemsListener;

public class CustomerCartFragmnet extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private CartManagement cartManagement;
    TextView totalPriceTxt, priceTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;


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
        scrollView= v.findViewById(R.id.cartScrollView);
        cartManagement = new CartManagement(getActivity());
        initList();
        CalculateCart();
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