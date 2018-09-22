package com.example.mokaposdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mokaposdemo.R;
import com.example.mokaposdemo.models.ShoppingTable;

import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter {

    private final ArrayList<ShoppingTable> items = new ArrayList<>();
    private final ShoppingCartListener listener;

    public ShoppingCartAdapter(ShoppingCartListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.shopping_cart_list_item, parent, false);
                viewHolder = new ShoppingCartAdapter.ItemViewHolder(v1);
                break;
            default:
                View v = inflater.inflate(R.layout.summary_list_item, parent, false);
                viewHolder = new ShoppingCartAdapter.SumViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case 0:
                ItemViewHolder vh1 = (ItemViewHolder) holder;
                setData1(vh1, position);
                break;
            default:
                SumViewHolder vh2 = (SumViewHolder) holder;
                setData2(vh2);
                break;
        }
    }

    private void setData2(SumViewHolder vh) {
        double subTotal = 0;
        double discount = 0;
        for (ShoppingTable item : items) {
            subTotal += item.getPrice() * item.getQuantity();
            discount += item.getPrice() * item.getDiscount() * item.getQuantity() / 100;
        }

        if (listener != null) listener.onUpdate(subTotal - discount);

        vh.subTotalAmount.setText("$ " + subTotal);
        vh.totalDiscountAmount.setText("($ " + discount + ")");

    }

    private void setData1(ItemViewHolder vh, int position) {
        ShoppingTable item = items.get(position);
        vh.title.setText(item.getTitle());
        vh.itemQuantity.setText("x" + item.getQuantity());
        vh.totalPrice.setText("$ " + item.getPrice() * item.getQuantity());

    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return 1;
        }

        return 0;
    }

    public void clearCart() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addItems(ShoppingTable item) {
        items.add(item);
        notifyDataSetChanged();
//        notifyItemInserted(getItemCount() - 2);
    }

    public void updateItems(ArrayList<ShoppingTable> values) {
        items.clear();
        items.addAll(values);
        notifyDataSetChanged();
    }

    public ArrayList<ShoppingTable> getItems() {
        return items;
    }


    public interface ShoppingCartListener {
        void onUpdate(double v);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView totalPrice;
        private TextView itemQuantity;
        private TextView title;

        ItemViewHolder(View itemView) {
            super(itemView);

            totalPrice = itemView.findViewById(R.id.total_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            title = itemView.findViewById(R.id.title);
        }
    }

    class SumViewHolder extends RecyclerView.ViewHolder {

        private TextView subTotalAmount;
        private TextView totalDiscountAmount;

        SumViewHolder(View itemView) {
            super(itemView);

            subTotalAmount = itemView.findViewById(R.id.sub_total_amount);
            totalDiscountAmount = itemView.findViewById(R.id.total_discount_amount);
        }
    }
}