package com.example.mokaposdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mokaposdemo.R;
import com.example.mokaposdemo.models.DiscountItem;

import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
    private final List<DiscountItem> items;

    public DiscountAdapter(List<DiscountItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DiscountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.discount_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountAdapter.ViewHolder holder, int position) {

        DiscountItem item = items.get(position);
        holder.discountName.setText(item.getName());
        holder.discountPercent.setText(item.getDiscount() + "%");

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView discountPercent;
        private TextView discountName;

        ViewHolder(View itemView) {
            super(itemView);

            discountPercent = itemView.findViewById(R.id.discount_percent);
            discountName = itemView.findViewById(R.id.discount_name);
        }
    }
}
