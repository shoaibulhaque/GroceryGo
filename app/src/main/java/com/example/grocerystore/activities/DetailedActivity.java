package com.example.grocerystore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.grocerystore.R;
import com.example.grocerystore.models.NewProductsModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating,name,description,price;
    Button addToCart, buyNow;
    ImageView addItems, removeItems;


    NewProductsModel newProductsModel = null;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        firestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_img);
        rating = findViewById(R.id.rating);
        name = findViewById(R.id.detailed_name);
        price = findViewById(R.id.detailed_price);
        description = findViewById(R.id.detailed_desc);
        addToCart = findViewById(R.id.add_to_cart);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);
        buyNow = findViewById(R.id.buy_now);

        if (newProductsModel != null){

            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            name.setText(newProductsModel.getName());
        }
    }
}