package com.asad.mnsuam.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asad.mnsuam.myapplication.Database.Database;
import com.asad.mnsuam.myapplication.Model.Items;
import com.asad.mnsuam.myapplication.Model.Order;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

public class ItemsDetailsActivity extends AppCompatActivity {
    TextView itemName, itemPrice, itemDescription;
    ImageView itemImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    String itemId = "";
    FirebaseDatabase database;
    DatabaseReference items;
    Items currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_details);
        numberButton = findViewById(R.id.btnCount);
        btnCart = findViewById(R.id.btn_cart);
        itemDescription = findViewById(R.id.item_des);
        itemPrice = findViewById(R.id.item_price);
        itemName = findViewById(R.id.item_name);
        itemImage = findViewById(R.id.img_itDetail);
        
        //Firebase
        database = FirebaseDatabase.getInstance();
        items = database.getReference("detail");
        //init view

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        itemId,
                        currentItem.getIname(),
                        numberButton.getNumber(),
                        currentItem.getPrice(),
                        currentItem.getDiscount()
                ));
                Toast.makeText(ItemsDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
        //Get Id from previous activity intent

        if (getIntent() != null)
            itemId = getIntent().getStringExtra("itemId");
        if (!itemId.isEmpty())
            getDetailItem(itemId);
    }

    private void getDetailItem(final String itemId) {
        items.child(itemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentItem = dataSnapshot.getValue(Items.class);
//              //setImage

                Picasso.with(getBaseContext()).load(currentItem.getImage())
                        .into(itemImage);

                if (items != null) {
                    collapsingToolbarLayout.setTitle(currentItem.getIname());
                }
                if (items != null) {
                    itemPrice.setText(currentItem.getPrice());
                }
                if (items != null) {
                    itemName.setText(currentItem.getIname());
                } else {
                    Toast.makeText(ItemsDetailsActivity.this, "Not Found" + itemId, Toast.LENGTH_SHORT).show();
                }
                if (items != null) {
                    itemDescription.setText(currentItem.getDescription());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
