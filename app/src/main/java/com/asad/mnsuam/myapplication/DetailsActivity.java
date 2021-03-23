package com.asad.mnsuam.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.asad.mnsuam.myapplication.Interface.ItemClickListener;
import com.asad.mnsuam.myapplication.Model.Items;
import com.asad.mnsuam.myapplication.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference itemList;
    String categoryId="";
    FirebaseRecyclerAdapter<Items, ItemViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //FireBase
        database = FirebaseDatabase.getInstance();
        itemList = database.getReference("detail");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewDetails);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //getting intent

        if (getIntent()!=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId!=null){
            loadItemList(categoryId);
        }

    }

    private void loadItemList(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Items, ItemViewHolder>(Items.class,R.layout.item_detail,
                ItemViewHolder.class,
                itemList.orderByChild("itemId").equalTo(categoryId)) //just like select * from items where itemId = category id
        {
            @Override
            protected void populateViewHolder(ItemViewHolder itemViewHolder, Items model, int position) {
                itemViewHolder.tvItemName.setText(model.getIname());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(itemViewHolder.ivItem);

                final Items local = model;
                itemViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent itemDetail = new Intent(DetailsActivity.this,ItemsDetailsActivity.class);
                        itemDetail.putExtra("itemId",adapter.getRef(position).getKey()); //Sending item ID to new activity
                        startActivity(itemDetail);
                    }
                });
            }
        };
        //adapter
        recyclerView.setAdapter(adapter);
    }
}
