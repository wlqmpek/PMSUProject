package com.example.pmsu_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.buyers.ViewArticleDetailsActivity;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.Order;

import java.util.ArrayList;
import java.util.List;

public class ListDeliveredArticlesAdapter extends RecyclerView.Adapter<ListDeliveredArticlesAdapter.MyViewHolder> {

    private Context context;
    private List<Article> articles = new ArrayList<>();
    private Order order;

    public ListDeliveredArticlesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_delivered_articles_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        showResponse("Article size " + articles.size());
        holder.name.setText(articles.get(position).getName());
        holder.quantity.setText(String.valueOf(order.getArticleQuantity().stream().filter(articleQuantity -> articleQuantity.getArticleId().equals(articles.get(position).getArticleId())).findFirst().get().getQuantity()));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.deliveredArticleName);
            quantity = itemView.findViewById(R.id.deliveredArticleQuantity);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, ViewArticleDetailsActivity.class);
            i.putExtra("Article", articles.get(getAdapterPosition()));
            context.startActivity(i);
        }
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void showResponse(String response) {
        Toast.makeText(context,response, Toast.LENGTH_LONG).show();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
