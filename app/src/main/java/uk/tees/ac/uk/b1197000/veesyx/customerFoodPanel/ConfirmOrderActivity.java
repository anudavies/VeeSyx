
package uk.tees.ac.uk.b1197000.veesyx.customerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import uk.tees.ac.uk.b1197000.veesyx.CustomerFoodPanel_BottomNavigation;
import uk.tees.ac.uk.b1197000.veesyx.R;
import uk.tees.ac.uk.b1197000.veesyx.db.CartManagement;

public class ConfirmOrderActivity extends AppCompatActivity {
    private EditText txtFullName ,txtPhoneNumber,txtHomeAddress,txtCityName;
    private TextView txtDisplayAmount;
    private Button Backbtn,ConfirmBtn;
    private String totalAmount="";
    private CartManagement cartManagement;
    DatabaseReference dataa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        totalAmount=getIntent().getStringExtra("Total Price");
        txtDisplayAmount = findViewById(R.id.displayAmount);
        txtDisplayAmount.setText("£"+ totalAmount);
        //Toast.makeText(this, "Total Price is :"+totalAmount, Toast.LENGTH_SHORT).show();
        txtFullName=(EditText)findViewById(R.id.shippment_name);
        txtPhoneNumber=(EditText)findViewById(R.id.shippment_phone);
        txtHomeAddress=(EditText)findViewById(R.id.shippment_home_address);
        txtCityName=(EditText)findViewById(R.id.shippment_city_name);
        Backbtn=(Button)findViewById(R.id.shippment_back_btn);
        ConfirmBtn=(Button)findViewById(R.id.shippment_confirm_btn);
        cartManagement = new CartManagement(this);
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Customer customer = snapshot.getValue(Customer.class);
                txtFullName.setText(customer.getFirstName() + " " + customer.getLastName());
                txtPhoneNumber.setText(customer.getMobileNo());
                txtHomeAddress.setText(customer.getLocalAddress());
                txtCityName.setText(customer.getState());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }
    private void check()
    {
        if(TextUtils.isEmpty(txtFullName.getText().toString()))
        {
            Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtPhoneNumber.getText().toString()))
        {
            Toast.makeText(this, "Phone number is Empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtHomeAddress.getText().toString()))
        {
            Toast.makeText(this, "Home Address is Empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtCityName.getText().toString()))
        {
            Toast.makeText(this, "City Name is Empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            confirmOrder();
        }

    }
    private void confirmOrder()
    {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calendar.getTime());
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String RandomUID = UUID.randomUUID().toString();
        final DatabaseReference OrdersRef= FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(userid)
                .child(RandomUID);


        HashMap<String ,Object> orderMap=new HashMap<>();
        orderMap.put("Total Amount",totalAmount);
        orderMap.put("Name",txtFullName.getText().toString());
        orderMap.put("Phone",txtPhoneNumber.getText().toString());
        orderMap.put("Address",txtHomeAddress.getText().toString());
        orderMap.put("City",txtCityName.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("State","not shipped");

        OrdersRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("User View")
                            .child(userid)
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmOrderActivity.this, "Order is Placed", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(ConfirmOrderActivity.this, CustomerFoodPanel_BottomNavigation.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        cartManagement.removeCartList();
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}