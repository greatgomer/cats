package com.example.cats.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.R;
import com.example.cats.ImageDetails;
import com.example.cats.api.models.req.FavoritesParameters;
import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.ImagesService;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatRecyclerAdapter extends RecyclerView.Adapter<CatRecyclerAdapter.CustomViewHolder>
        implements View.OnClickListener {

    private ImagesService service;
    private List<Cat> dataList;
    private Context context;
    public String imageId;
    public ImageView imageView;

    public CatRecyclerAdapter(ImagesService service, Context context, List<Cat> dataList) {
        this.service = service;
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
            coverImage = mView.findViewById(R.id.cat_image);
        }
    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cat_item, parent, false);
        imageView = view.findViewById(R.id.button_put_in_favourites);

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

        imageView.setOnClickListener(view -> {
            imageId = dataList.get(position).getId();
            FavoritesParameters favoritesParameters = new FavoritesParameters(imageId);
            service.postFavourites(favoritesParameters).enqueue(new Callback<FavoritesParameters>() {
                @Override
                public void onResponse(@NotNull Call<FavoritesParameters> call, @NotNull Response<FavoritesParameters> response) {

                }

                @Override
                public void onFailure(@NotNull Call<FavoritesParameters> call, @NotNull Throwable t) {
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}