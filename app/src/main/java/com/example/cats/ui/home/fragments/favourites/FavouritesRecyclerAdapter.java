package com.example.cats.ui.home.fragments.favourites;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cats.ui.home.fragments.favourites.favouritesDialog.FavouritesDialogActivity;
import com.example.cats.ui.image.ImageDetails;
import com.example.cats.R;
import com.example.cats.api.models.res.Favorites;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavouritesRecyclerAdapter extends RecyclerView.Adapter<FavouritesRecyclerAdapter.CustomViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {

    private List<Favorites> dataList;
    private Context context;
    String test = null;
    public static Integer idImage;

    public FavouritesRecyclerAdapter(Context context, List<Favorites> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            coverImage = mView.findViewById(R.id.favourite_image);
        }
    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.favourite_item, parent, false);

        return new CustomViewHolder(view);
    }

    public void onBindViewHolder(@NotNull CustomViewHolder holder, int position) {
        FavouritesFragmentViewModel.favouritesAllId.add(dataList.get(position).getImage().getUrl());
        try {
            test = dataList.get(position).getImage().getUrl();
        } catch (NullPointerException e) {
            Toast.makeText(context, "No images", Toast.LENGTH_LONG).show();
        }

        Glide.with(context)
                .load(test)
                .centerCrop()
                .into(holder.coverImage);

        holder.mView.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(context, ImageDetails.class);
            intent.putExtra("url", dataList.get(position).getImage().getUrl());
            intent.putExtra("id", dataList.get(position).getImage().getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        holder.mView.setOnLongClickListener(view -> {
            idImage = dataList.get(position).getId();
            Intent intent = new Intent();
            intent.setClass(context, FavouritesDialogActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            return false;
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
