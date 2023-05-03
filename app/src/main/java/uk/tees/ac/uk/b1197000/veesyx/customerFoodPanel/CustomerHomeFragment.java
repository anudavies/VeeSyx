package uk.tees.ac.uk.b1197000.veesyx.customerFoodPanel;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.tees.ac.uk.b1197000.veesyx.MainMenu;
import uk.tees.ac.uk.b1197000.veesyx.R;
import uk.tees.ac.uk.b1197000.veesyx.UpdateDishModel;


public class CustomerHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerView;
    private List<UpdateDishModel> updateDishModelList;
    private CustomerHomeAdapter adapter;
    String State,City,Area;
    TextView Location;
    DatabaseReference dataa,databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;
    FusedLocationProviderClient fusedLocationProviderClient;
    private  final  static int REQUEST_CODE=100;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerhome, null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);
        recyclerView = v.findViewById(R.id.recycle_menu);
        Location = v.findViewById(R.id.city);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getActivity());
        recyclerView.setHasFixedSize(true);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.move);
        recyclerView.startAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateDishModelList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.Red);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                String  userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                dataa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Customer custo = snapshot.getValue(Customer.class);
                        State = custo.getState();
                        City = custo.getCity();
                        Area = custo.getArea();
                        customermenu();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        getLastLocation();

        return v;
    }
    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null){
                                Geocoder geocoder=new Geocoder(getActivity(), Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    Location.setText(addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getCountryName());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }else
        {
            askPermission();
        }
    }
    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(getActivity(), "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    @Override
    public void onRefresh() {
        customermenu();
    }

    private void customermenu() {

        swipeRefreshLayout.setRefreshing(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("FoodDetails").child(State).child(City).child(Area);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateDishModelList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    for(DataSnapshot snapshot2 : snapshot1.getChildren()){
                        UpdateDishModel updateDishModel = snapshot2.getValue(UpdateDishModel.class);
                        updateDishModelList.add(updateDishModel);
                    }
                }
                adapter = new CustomerHomeAdapter(getContext(),updateDishModelList,null);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }

}