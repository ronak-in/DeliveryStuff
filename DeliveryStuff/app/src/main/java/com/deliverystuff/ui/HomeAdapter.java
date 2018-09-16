package com.deliverystuff.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deliverystuff.R;
import com.deliverystuff.model.ModelDelivery;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<ModelDelivery> data = new ArrayList<>();
    private Context context;

    public HomeAdapter(Context context, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    public void setData(List<ModelDelivery> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.click(data.get(position), listener);
        holder.tvDescription.setText(data.get(position).getDescription());
        holder.tvLocationName.setText(data.get(position).getLocation().getAddress());
        Glide.with(context)
                .load(data.get(position).getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .thumbnail(0.1f)
                .into(holder.imgLogo);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnItemClickListener {
        void onClick(ModelDelivery Item, View view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvLocationName;
        ImageView imgLogo;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            imgLogo = itemView.findViewById(R.id.imgLogo);
        }


        public void click(final ModelDelivery modelDelivery, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(modelDelivery, v);
                }
            });
        }
    }


}
