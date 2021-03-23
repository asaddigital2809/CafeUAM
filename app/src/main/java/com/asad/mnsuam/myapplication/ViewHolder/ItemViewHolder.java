package com.asad.mnsuam.myapplication.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.asad.mnsuam.myapplication.Interface.ItemClickListener;
import com.asad.mnsuam.myapplication.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvItemName;
    public ImageView ivItem;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        tvItemName = (TextView)itemView.findViewById(R.id.itemName);
        ivItem= (ImageView)itemView.findViewById(R.id.item_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
