package com.example.ohjelmistoprojekti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HH_RecyclerViewAdapter extends RecyclerView.Adapter<HH_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<HappyHourItem> happyHourList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing views from recycler_view_row layout file
        // acting as an onCreate method (bad analogy)

        TextView mTitle, mFrom, mTo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.editTitle);
            mFrom = itemView.findViewById(R.id.editFrom);
            mTo = itemView.findViewById(R.id.editTo);
        }

    }

    public HH_RecyclerViewAdapter(Context context, ArrayList<HappyHourItem> happyHourList){
        this.context = context;
        this.happyHourList = happyHourList;
    }

    @NonNull
    @Override
    public HH_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating layout and giving a look to the rows
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new HH_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HH_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // assigning values to views created in the recycler_view_row layout file
        // based on the position of the recycler view
        holder.mTitle.setText(happyHourList.get(position).getTitle());
        holder.mFrom.setText(happyHourList.get(position).getFrom());
        holder.mTo.setText(happyHourList.get(position).getTo());
    }

    @Override
    public int getItemCount() {
        // recycler view wants to know the number of items we want displayed
        return happyHourList.size();
    }


}
