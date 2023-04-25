package uk.tees.ac.uk.b1197000.veesyx.customerFoodPanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import uk.tees.ac.uk.b1197000.veesyx.CustomerFoodPanel_BottomNavigation;
import uk.tees.ac.uk.b1197000.veesyx.R;
import uk.tees.ac.uk.b1197000.veesyx.UpdateDishModel;
import uk.tees.ac.uk.b1197000.veesyx.db.CartManagement;
import uk.tees.ac.uk.b1197000.veesyx.entities.Food;

public class ShowDetailsActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private UpdateDishModel object;
    int numberOrder = 1;
    private Context mcontext;
    private CartManagement cartManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        cartManagement = new CartManagement(this);
        initComponents();

        getBundle();
    }
    private void getBundle(){

        object = (UpdateDishModel) getIntent().getSerializableExtra("object");

        String txtImage = object.getDishes();


        Picasso.get().load(txtImage).into(picFood);
        titleTxt.setText(object.getDishes());
        feeTxt.setText("£" + object.getPrice());
        //  descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));

            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder - 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));

            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(numberOrder);
                cartManagement.insertFood(object);
                startActivity(new Intent(ShowDetailsActivity.this, CustomerFoodPanel_BottomNavigation.class));

            }
        });
    }

    private void initComponents(){
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.textTitle);
        feeTxt = findViewById(R.id.priceText);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.detailsFoodPic);
    }
}