package com.asad.mnsuam.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asad.mnsuam.myapplication.Database.Database;
import com.asad.mnsuam.myapplication.Model.Common;
import com.asad.mnsuam.myapplication.Model.Order;
import com.asad.mnsuam.myapplication.Model.Request;
import com.asad.mnsuam.myapplication.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtTotal;
    Button btnPlace;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //FireBase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("requests");
        //Init View
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtTotal = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.isEmpty()){
                    Toast.makeText(Cart.this, "Please select some items", Toast.LENGTH_SHORT).show();
                }else {
                showAlertDialog();
                }
            }
        });
        loadListItem();

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter Your Room No or Address:");
        final EditText edtAdd = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        edtAdd.setLayoutParams(lp);
        alertDialog.setView(edtAdd); //add edit text to alert dialog
        alertDialog.setIcon(R.drawable.cart_ic);
//        alertDialog.setMessage("Confirm Your Order");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Request request = new Request(
                        Common.saveCurrentUser.getPhone(),
                        Common.saveCurrentUser.getName(),
                        edtAdd.getText().toString(),
                        txtTotal.getText().toString(),
                        cart
                );
                //Submit to Firebase
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                //delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Thank You! Order Placed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListItem() {
        cart = new Database(getApplicationContext()).getCarts();
        adapter = new CartAdapter(cart,getApplicationContext());
        recyclerView.setAdapter(adapter);

        //calculate total price
        int total = 0;
        for (Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("ur","PK");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        txtTotal.setText(format.format(total));
    }
}
