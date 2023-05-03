package uk.tees.ac.uk.b1197000.veesyx.deliveryFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.tees.ac.uk.b1197000.veesyx.MainMenu;
import uk.tees.ac.uk.b1197000.veesyx.OrdersModel;
import uk.tees.ac.uk.b1197000.veesyx.R;
import uk.tees.ac.uk.b1197000.veesyx.customerFoodPanel.OrderListAdapter;


public class DeliveryPendingOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerView;
    private List<OrdersModel> ordersList;
    private OrderListAdapter adapter;
    DatabaseReference data,databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_deliverypendingorder, null);
        getActivity().setTitle("Pending Orders");
        setHasOptionsMenu(true);
        recyclerView = v.findViewById(R.id.Recycle_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.Red);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
            customerOrders();
            }
        });
        return v;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idd = item.getItemId();
        if(idd == R.id.LOGOUT){
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void Logout() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void customerOrders(){
        swipeRefreshLayout.setRefreshing(true);
        String  userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ordersList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    for(DataSnapshot snapshot2 : snapshot1.getChildren()){
                        OrdersModel orders = snapshot2.getValue(OrdersModel.class);
                        ordersList.add(orders);
                    }


                }
                adapter = new OrderListAdapter(getContext(),ordersList,null);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    @Override
    public void onRefresh() {
        customerOrders();
    }
}
