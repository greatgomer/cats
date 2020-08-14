package com.example.cats.ui.home.fragments.cats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.R;
import com.example.cats.api.models.req.FavoritesParameters;
import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.ui.home.fragments.cats.authorisation.AuthorisationActivity;
import com.example.cats.ui.home.fragments.favourites.FavouritesFragmentViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatsAdapter extends PagedListAdapter<Cat, CatsAdapter.ItemViewHolder> implements View.OnClickListener{
    FavouritesService service;
    private Context context;
    ImageView imageView;

    private static DiffUtil.ItemCallback<Cat> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Cat>() {
                @Override
                public boolean areItemsTheSame(Cat oldItem, Cat newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Cat oldItem, @NotNull Cat newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public CatsAdapter(FavouritesService service, Context context) {
        super(DIFF_CALLBACK);
        this.service = service;
        this.context = context;
    }

    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cat_item, parent, false);

        return new ItemViewHolder(view);
    }

    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Cat cat = getItem(position);
        assert cat != null;
        Picasso.with(context)
                .load(cat.getUrl())
                .resize(500, 500)
                .centerCrop()
                .error(R.drawable.image_background)
                .into(holder.coverImage);

        imageView.setOnClickListener(view -> {
            if (!FavouritesFragmentViewModel.favouritesAllId.contains(cat.getId())) {
                FavoritesParameters favoritesParameters = new FavoritesParameters(cat.getId(), AuthorisationActivity.userName);
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
    public void onClick(View view) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImage;

        public ItemViewHolder(View view) {
            super(view);
            coverImage = view.findViewById(R.id.cat_image);
            imageView = view.findViewById(R.id.button_put_in_favourites);
        }
    }

}