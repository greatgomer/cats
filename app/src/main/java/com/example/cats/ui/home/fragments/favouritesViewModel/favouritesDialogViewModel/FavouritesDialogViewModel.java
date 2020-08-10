package com.example.cats.ui.home.fragments.favouritesViewModel.favouritesDialogViewModel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.R;
import com.example.cats.api.models.req.DeleteFromFavourites;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.di.MyApplication;
import com.example.cats.ui.home.MainActivity;
import com.example.cats.ui.home.fragments.favouritesViewModel.FavouritesRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesDialogViewModel extends AndroidViewModel {
    @Inject
    FavouritesService service;

    public FavouritesDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public void deleteFavourite(){
        Context context = getApplication();
        ((MyApplication) getApplication().getApplicationContext()).appComponent.favourites(this);
        service.deleteFromFavorites(FavouritesRecyclerAdapter.idImage).enqueue(new Callback<DeleteFromFavourites>() {
            @Override
            public void onResponse(@NotNull Call<DeleteFromFavourites> call, @NotNull Response<DeleteFromFavourites> response) {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NotNull Call<DeleteFromFavourites> call, @NotNull Throwable t) {
                Toast.makeText(context, "Decline", Toast.LENGTH_SHORT).show();
            }
        });
        MainActivity.navController.navigate(R.id.favoritesFragment);
    }

}