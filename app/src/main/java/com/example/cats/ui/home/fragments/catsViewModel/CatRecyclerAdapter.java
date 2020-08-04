package com.example.cats.ui.home.fragments.catsViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.R;
import com.example.cats.ui.home.fragments.favouritesViewModel.FavouritesFragmentViewModel;
import com.example.cats.ui.imageViewModel.ImageDetails;
import com.example.cats.api.models.req.FavoritesParameters;
import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.FavouritesService;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatRecyclerAdapter extends RecyclerView.Adapter<CatRecyclerAdapter.CustomViewHolder>
        implements View.OnClickListener {

    private FavouritesService service;
    private List<Cat> dataList;
    private Context context;
    public ImageView imageView;
    SharedPreferences sharedPreferences;
    String email = "";

    public CatRecyclerAdapter(FavouritesService service, Context context, List<Cat> dataList) {
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
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);

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
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });

        imageView.setOnClickListener(view -> {
            if (!FavouritesFragmentViewModel.favouritesAllId.contains(dataList.get(position).getUrl())) {
                email = sharedPreferences.getString("email", "default value");
                FavoritesParameters favoritesParameters = new FavoritesParameters(dataList.get(position).getId(), email);
                service.postFavourites(favoritesParameters).enqueue(new Callback<FavoritesParameters>() {
                    @Override
                    public void onResponse(@NotNull Call<FavoritesParameters> call, @NotNull Response<FavoritesParameters> response) {
                        Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NotNull Call<FavoritesParameters> call, @NotNull Throwable t) {
                        Toast.makeText(context, "Decline", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}