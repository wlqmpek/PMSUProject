package com.example.pmsu_project.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.pmsu_project.activities.sellers.EditArticleActivity;
import com.example.pmsu_project.models.Order;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListDeliveredOrdersAdapter extends RecyclerView.Adapter<ListDeliveredOrdersAdapter.MyViewHolder> {

    private Context context;
    private List<Order> orders = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public ListDeliveredOrdersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_delivered_orders_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listDeliveredOrdersTextViewId.setText(String.valueOf(orders.get(position).getOrderId()));
        holder.listDeliveredOrdersTextViewTime.setText(orders.get(position).getTime().format(formatter));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView listDeliveredOrdersTextViewId, listDeliveredOrdersTextViewTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            listDeliveredOrdersTextViewId = itemView.findViewById(R.id.listDeliveredOrdersIdTextView);
            listDeliveredOrdersTextViewTime = itemView.findViewById(R.id.listDeliveredOrdersTimeTextView);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, EditArticleActivity.class);
            i.putExtra("order", orders.get(getAdapterPosition()));
            context.startActivity(i);
        }
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void showResponse(String response) {
        Toast.makeText(context ,response, Toast.LENGTH_SHORT).show();
    }
}
