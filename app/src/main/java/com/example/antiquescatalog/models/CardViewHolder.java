package com.example.antiquescatalog.models;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antiquescatalog.R;

public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final CardView cardView;
    public final TextView tv_title,tv_category;
    //private AdapterOnItemClickListener mClickListener;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.card);
        tv_title = itemView.findViewById(R.id.ii_tv_title);
        tv_category = itemView.findViewById(R.id.ii_tv_category);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        CatalogAdapter.mClickListener.onItemClick(getAdapterPosition(), v);
    }

    public void setCard(Item i) {
        tv_title.setText(i.getTitle());
        tv_category.setText(i.getCategory());
    }


}
