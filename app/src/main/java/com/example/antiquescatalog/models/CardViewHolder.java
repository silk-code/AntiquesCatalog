package com.example.antiquescatalog.models;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antiquescatalog.R;
import com.example.antiquescatalog.interfaces.AdapterOnItemClickListener;

public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final CardView cardView;
    public final TextView tv_title, tv_condition, tv_time_period, tv_category;
    //private AdapterOnItemClickListener mClickListener;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.card);
        tv_title = itemView.findViewById(R.id.ic_tv_title);
        tv_category = itemView.findViewById(R.id.ic_tv_category);
        tv_time_period = itemView.findViewById(R.id.ic_tv_time_period);
        tv_condition = itemView.findViewById(R.id.ic_tv_condition);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        CatalogAdapter.mClickListener.onItemClick(getAdapterPosition(), v);
    }

    public void setCard(Item i) {
        tv_title.setText(i.getTitle());
        tv_category.setText(i.getCategory());
        tv_time_period.setText(i.getTimePeriod());
        tv_condition.setText(i.getCondition());
    }


}
