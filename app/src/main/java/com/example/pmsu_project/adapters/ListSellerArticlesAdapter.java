package com.example.pmsu_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.R;
import com.example.pmsu_project.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ListSellerArticlesAdapter extends RecyclerView.Adapter<ListSellerArticlesAdapter.MyViewHolder> {

    private Context context;
    private List<Article> articles = new ArrayList<>();
    private List<EditText> editTexts = new ArrayList<>();


    public ListSellerArticlesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_seller_articles_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(articles.get(position).getName());
        holder.price.setText(String.valueOf(articles.get(position).getPrice()));
        holder.aSwitch.setChecked(articles.get(position).isOnSale());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price;
        Switch aSwitch;
        EditText quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.listArticleNameTextView1);
            price = itemView.findViewById(R.id.listArticlePriceTextView2);
            aSwitch = itemView.findViewById(R.id.listArticleOnSaleSwitch1);
            aSwitch.setClickable(false);
            quantity = itemView.findViewById(R.id.listArticleQuantityEditText1);
            editTexts.add(quantity);

//            quantity.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
        }
    }

    public List<EditText> getEditTexts() {
        return editTexts;
    }

    public void setEditTexts(List<EditText> editTexts) {
        this.editTexts = editTexts;
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

}
