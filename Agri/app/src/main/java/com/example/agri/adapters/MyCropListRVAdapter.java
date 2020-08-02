package com.example.agri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agri.R;
import com.example.agri.pojos.Crops;

import java.util.List;

public class MyCropListRVAdapter extends RecyclerView.Adapter<MyCropListRVAdapter.MyCropItemViewHolder> {

    private Context context;
    private List<Crops> cropsList;

    public MyCropListRVAdapter(Context context, List<Crops> cropsList) {
        this.context = context;
        this.cropsList = cropsList;
    }

    @NonNull
    @Override
    public MyCropItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(context).inflate(R.layout.farmer_crop_list_item, parent, false);
        return new MyCropItemViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCropItemViewHolder holder, int position) {
        Crops crop = cropsList.get(position);
        holder.cropNameTV.setText(crop.getCropName());
        holder.cropExpectedDateTV.setText(crop.getExpectedDate().toString());
        holder.cropTotalQuantityTV.setText(crop.getTotalQuantity());
        holder.cropRemainingQuantityTV.setText(crop.getRemainingQuantity());
        holder.cropIsOrganicTV.setText(crop.getOrganic().toString());
        holder.cropPriceTV.setText(crop.getPrice());
    }

    @Override
    public int getItemCount() {
        return cropsList.size();
    }

    static class MyCropItemViewHolder extends RecyclerView.ViewHolder {
        TextView cropNameTV, cropExpectedDateTV, cropTotalQuantityTV, cropRemainingQuantityTV, cropIsOrganicTV, cropPriceTV;

        MyCropItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cropNameTV = itemView.findViewById(R.id.mu_crop_name);
            this.cropExpectedDateTV = itemView.findViewById(R.id.my_crop_expected_date);
            this.cropTotalQuantityTV = itemView.findViewById(R.id.totalQuantity);
            this.cropRemainingQuantityTV = itemView.findViewById(R.id.my_crop_remaining_quantity);
            this.cropIsOrganicTV = itemView.findViewById(R.id.my_crop_isOrganic);
            this.cropPriceTV = itemView.findViewById(R.id.my_crop_price);
        }
    }
}
