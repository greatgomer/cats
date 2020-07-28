package com.example.cats.favorites;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.ImageDetails;
import com.example.cats.MainActivity;
import com.example.cats.R;
import com.example.cats.api.models.req.DeleteFromFavourites;
import com.example.cats.api.models.res.Favorites;
import com.example.cats.api.services.ImagesService;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesRecyclerAdapter extends RecyclerView.Adapter<FavoritesRecyclerAdapter.CustomViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {

    AlertDialog.Builder builder;
    private ImagesService service;
    private List<Favorites> dataList;
    private Context context;
    private MainActivity mainActivity;
    String test = null;
    Integer idImage;

    public FavoritesRecyclerAdapter(ImagesService service, Context context, List<Favorites> dataList) {
        this.service = service;
        this.context = context;
        this.dataList = dataList;
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    static class CustomViewHolder extends RecyclerView.ViewHolder {

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
        dialogForFavorites();
        return new CustomViewHolder(view);
    }

    public void onBindViewHolder(@NotNull CustomViewHolder holder, int position) {
        try {
            test = dataList.get(position).getImage().getUrl();
        } catch (NullPointerException e) {
            Toast.makeText(context, "No images", Toast.LENGTH_LONG).show();
        }

        Picasso.with(context)
                .load(test)
                .resize(500, 500)
                .centerCrop()
                .error(R.drawable.image_background)
                .into(holder.coverImage);

        holder.mView.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(context, ImageDetails.class);
            intent.putExtra("url", dataList.get(position).getImage().getUrl());
            context.startActivity(intent);
        });

        holder.mView.setOnLongClickListener(view -> {
            idImage = dataList.get(position).getId();
            builder.create()
                    .show();

            return false;
        });
    }

    public void dialogForFavorites() {
        builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.interface_ok, (dialog, id) -> {
                    service.deleteFromFavorites(idImage).enqueue(new Callback<DeleteFromFavourites>() {
                        @Override
                        public void onResponse(@NotNull Call<DeleteFromFavourites> call, @NotNull Response<DeleteFromFavourites> response) {

                        }

                        @Override
                        public void onFailure(@NotNull Call<DeleteFromFavourites> call, @NotNull Throwable t) {

                        }
                    });
                    dataList.clear();
                    mainActivity.getSupportFragmentManager().beginTransaction().detach(mainActivity.fragment2).attach(mainActivity.fragment2).commit();
                })
                .setNegativeButton(R.string.interface_cancel, (dialog, id) -> dialog.cancel());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
