package com.example.pmsu_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.ListSellerArticlesActivity;
import com.example.pmsu_project.activities.ListSellersActivity;
import com.example.pmsu_project.models.Seller;

import java.util.ArrayList;
import java.util.List;

public class ListSellersRecycleViewAdapter extends RecyclerView.Adapter<ListSellersRecycleViewAdapter.MyViewHolder> {

    private Context context;
    private List<Seller> sellers = new ArrayList<>();

    public ListSellersRecycleViewAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_sellers_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listSellersTextViewName.setText(sellers.get(position).getName());
        holder.listSellersTextViewRating.setText(String.valueOf(sellers.get(position).getRating()));
        holder.listSellersButtonArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListSellerArticlesActivity.class);

                i.putExtra("Seller", sellers.get(holder.getAdapterPosition()));
                context.startActivity(i);
            }
        });

        holder.listSellersButtonDiscounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListSellerArticlesActivity.class);
                i.putExtra("Seller", sellers.get(holder.getAdapterPosition()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listSellersTextViewName, listSellersTextViewRating;
        Button listSellersButtonDiscounts, listSellersButtonArticles;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            listSellersTextViewName = itemView.findViewById(R.id.listSellersTextViewName);
            listSellersTextViewRating = itemView.findViewById(R.id.listSellersTextViewRating);
            listSellersButtonArticles = itemView.findViewById(R.id.listSellersButtonArticles);
            listSellersButtonDiscounts = itemView.findViewById(R.id.listSellersButtonDiscounts);
        }
    }

}
