package com.example.antiquescatalog.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antiquescatalog.R;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<Item> mData;
    private LayoutInflater mInflater;
    //private ItemClickListener mClickListener;

    public CatalogAdapter(Context context, List<Item> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Item i = mData.get(position);
        holder.setCard(i);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
