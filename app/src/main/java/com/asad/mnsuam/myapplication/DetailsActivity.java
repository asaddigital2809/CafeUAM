package com.asad.mnsuam.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.asad.mnsuam.myapplication.Interface.ItemClickListener;
import com.asad.mnsuam.myapplication.Model.Items;
import com.asad.mnsuam.myapplication.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference itemList;
    String categoryId="";
    FirebaseRecyclerAdapter<Items, ItemViewHolder> adapter;
    //search functionality
    FirebaseRecyclerAdapter<Items, ItemViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
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
        //search
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchFood);
        materialSearchBar.setHint("Enter Your Food");
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        //change suggest list when user types
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<String>();
                for (String search:suggestList){
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when searchbar is close restore original adapter
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //show result when finish
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }

    private void startSearch(CharSequence text) {
            searchAdapter = new FirebaseRecyclerAdapter<Items, ItemViewHolder>(
                    Items.class,
                    R.layout.item_detail,
                    ItemViewHolder.class,
                    itemList.orderByChild("Name").equalTo(text.toString())
            ){
                @Override
                protected void populateViewHolder(ItemViewHolder itemViewHolder, Items model, int i) {
                    itemViewHolder.tvItemName.setText(model.getIname());
                    Picasso.with(getBaseContext()).load(model.getImage())
                            .into(itemViewHolder.ivItem);

                    final Items local = model;
                    itemViewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent itemDetail = new Intent(DetailsActivity.this,ItemsDetailsActivity.class);
                            itemDetail.putExtra("itemId",searchAdapter.getRef(position).getKey()); //Sending item ID to new activity
                            startActivity(itemDetail);
                        }
                    });
                }
            };
            recyclerView.setAdapter(searchAdapter);


    }


    private void loadSuggest() {
        itemList.orderByChild("itemId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                            Items items = postSnapShot.getValue(Items.class);
                            suggestList.add(items.getIname());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
