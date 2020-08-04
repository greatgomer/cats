package com.example.cats.ui.dialogViewModel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.R;
import com.example.cats.api.models.req.DeleteFromFavourites;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.databinding.ActivityDialogBinding;
import com.example.cats.di.MyApplication;
import com.example.cats.ui.home.MainActivity;
import com.example.cats.ui.home.fragments.favouritesViewModel.FavouritesRecyclerAdapter;
import com.jakewharton.rxbinding4.view.RxView;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogViewModel extends AndroidViewModel {
    @Inject
    FavouritesService service;

    public DialogViewModel(@NonNull Application application) {
        super(application);
    }

    public void dialogViewModel(ActivityDialogBinding binding) {
        Context context = getApplication();
        ((MyApplication) getApplication().getApplicationContext()).appComponent.favourites(this);
        RxView.clicks(binding.buttonYes).subscribe(aVoid ->{
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
        }).isDisposed();
    }
}
