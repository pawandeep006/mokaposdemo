package com.example.mokaposdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mokaposdemo.R;
import com.example.mokaposdemo.database.tables.ArticleTable;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private final List<ArticleTable> items;

    public ArticleAdapter(List<ArticleTable> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.article_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {

        ArticleTable item = items.get(position);
        holder.articleName.setText(item.getName());
        holder.articlePrice.setText("$ " + item.getPrice());

        Glide.with(holder.articleImage.getContext())
                .load(item.getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.articleImage);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public ArticleTable getItem(int position) {
        if (items.size() <= position) {
            return null;
        }
        return items.get(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView articleImage;
        private TextView articleName;
        private TextView articlePrice;

        ViewHolder(View itemView) {
            super(itemView);

            articleImage = itemView.findViewById(R.id.article_image);
            articleName = itemView.findViewById(R.id.article_name);
            articlePrice = itemView.findViewById(R.id.article_price);
        }
    }
}
