package com.example.cats.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.R;
import com.example.cats.activities.ImageDetails;
import com.example.cats.model.Cat;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CatRecyclerAdapter extends RecyclerView.Adapter<CatRecyclerAdapter.CustomViewHolder>
implements View.OnClickListener{

    private List<Cat> dataList;
    private Context context;

    public CatRecyclerAdapter(Context context, List<Cat> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public void onClick(View view) {

    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            coverImage = mView.findViewById(R.id.item_image);
        }
    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cat_item, parent, false);
        return new CustomViewHolder(view);
    }

    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Picasso.with(context)
                .load(dataList.get(position).getUrl())
                .resize(500, 500)
                .centerCrop()
                .error(R.drawable.image_background)
                .into(holder.coverImage);

        holder.mView.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(context, ImageDetails.class);
            intent.putExtra("url", dataList.get(position).getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}