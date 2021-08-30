package com.example.pmsu_project.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.R;
import com.example.pmsu_project.dtos.CreateArticleQuantityDTO;
import com.example.pmsu_project.dtos.CreateInitialOrderDTO;
import com.example.pmsu_project.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ListFinishOrderArticlesAdapter extends RecyclerView.Adapter<ListFinishOrderArticlesAdapter.MyViewHolder> {

    private Context context;
    private CreateInitialOrderDTO createInitialOrderDTO = new CreateInitialOrderDTO();
    private List<Article> articles = new ArrayList<>();

    public ListFinishOrderArticlesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_buyer_articles_row, parent, false);

        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(articles.stream().filter(article -> article.getArticleId().equals(createInitialOrderDTO.getArticleQuantity().get(position).getArticleId())).findFirst().get().getName());
        holder.quantity.setText(String.valueOf(createInitialOrderDTO.getArticleQuantity().get(position).getQuantity()));
        holder.price.setText(articles.stream().filter(article -> article.getArticleId().equals(createInitialOrderDTO.getArticleQuantity().get(position).getArticleId())).findFirst().get().getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return createInitialOrderDTO.getArticleQuantity().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.listBuyerOrderArticleNameTextView);
            price = itemView.findViewById(R.id.listBuyerOrderArticlePriceTextView);
            quantity = itemView.findViewById(R.id.listBuyerOrderArticleQuantity);
        }
    }

    public CreateInitialOrderDTO getCreateInitialOrderDTO() {
        return createInitialOrderDTO;
    }

    public void setCreateInitialOrderDTO(CreateInitialOrderDTO createInitialOrderDTO) {
        this.createInitialOrderDTO = createInitialOrderDTO;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public double calculateTotal() {
        Double total = 0.0;
        for(CreateArticleQuantityDTO articleQuantityDTO:createInitialOrderDTO.getArticleQuantity()) {
            total += articles.stream().filter(article -> article.getArticleId().equals(articleQuantityDTO.getArticleId())).findFirst().get().getPrice() * articleQuantityDTO.getQuantity();
        }
        return Math.round(total * 100.0)/100.0;
    }
}
