package com.example.pmsu_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.sellers.EditArticleActivity;
import com.example.pmsu_project.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ListSellerArticles2Adapter extends RecyclerView.Adapter<ListSellerArticles2Adapter.MyViewHolder> {

    private Context context;
    private List<Article> articles = new ArrayList<>();

    public ListSellerArticles2Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_seller_articles2_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSellerArticles2Adapter.MyViewHolder holder, int position) {
        holder.name.setText(articles.get(holder.getAdapterPosition()).getName());
        holder.price.setText(String.valueOf(articles.get(holder.getAdapterPosition()).getPrice()));
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResponse("Delete");
            }
        });
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditArticleActivity.class);
                i.putExtra("articleId", articles.get(holder.getAdapterPosition()).getArticleId());
                context.startActivity(i);
                showResponse("Edit");
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        Button buttonEdit, buttonDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.listArticleNameTextView2);
            price = itemView.findViewById(R.id.listArticlePriceTextView2);
            buttonEdit = itemView.findViewById(R.id.listSellerArticles2EditButton);
            buttonDelete = itemView.findViewById(R.id.listSellerArticles2DeleteButton);
        }
    }

    public void showResponse(String response) {
        Toast.makeText(context,response, Toast.LENGTH_LONG).show();
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
