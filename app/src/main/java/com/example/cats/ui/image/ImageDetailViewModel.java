package com.example.cats.ui.image;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.cats.api.models.req.FavoritesParameters;
import com.example.cats.api.models.res.Image;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.api.services.ImageService;
import com.example.cats.databinding.ActivityImageDetailsBinding;
import com.example.cats.di.MyApplication;
import com.example.cats.ui.home.fragments.cats.authorisation.AuthorisationActivity;
import com.example.cats.ui.home.fragments.favourites.FavouritesFragmentViewModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageDetailViewModel extends AndroidViewModel {
    @Inject
    ImageService service;

    @Inject
    FavouritesService serviceFavourite;

    public MutableLiveData<String> resultName = new MutableLiveData<>();
    public MutableLiveData<String> resultCfaUrl = new MutableLiveData<>();
    public MutableLiveData<String> resultDescription = new MutableLiveData<>();
    public MutableLiveData<String> resultTemperament = new MutableLiveData<>();
    String id;

    public ImageDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setImage(ActivityImageDetailsBinding binding, Bundle arguments) {
        ((MyApplication) getApplication().getApplicationContext()).appComponent.votes(this);
        ((MyApplication) getApplication().getApplicationContext()).appComponent.favourite(this);
        Context context = getApplication();
        String url = (String) arguments.get("url");
        id = (String) arguments.get("id");
        getImageInfo();

        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(binding.fullCat);
    }

    public void postVote() {
        if (!FavouritesFragmentViewModel.favouritesAllId.contains(id)) {
            FavoritesParameters favoritesParameters = new FavoritesParameters(id, AuthorisationActivity.userName);
            serviceFavourite.postFavourites(favoritesParameters).enqueue(new Callback<FavoritesParameters>() {
                @Override
                public void onResponse(@NotNull Call<FavoritesParameters> call, @NotNull Response<FavoritesParameters> response) {
                    Toast.makeText(getApplication().getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NotNull Call<FavoritesParameters> call, @NotNull Throwable t) {
                    Toast.makeText(getApplication().getApplicationContext(), "Decline", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getImageInfo(){
        service.image(id).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(@NotNull Call<Image> call, @NotNull Response<Image> response) {
                generateData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<Image> call, @NotNull Throwable t) {
                Log.d("FAIL", String.valueOf(t));
            }
        });
    }

    public LiveData<String> getResultName() {
        if (resultName == null) {
            resultName = new MutableLiveData<>();
        }
        return resultName;
    }

    public LiveData<String> getResultCfaUrl() {
        if (resultCfaUrl == null) {
            resultCfaUrl = new MutableLiveData<>();
        }
        return resultCfaUrl;
    }

    public LiveData<String> getResultDescription() {
        if (resultDescription == null) {
            resultDescription = new MutableLiveData<>();
        }
        return resultDescription;
    }

    public LiveData<String> getResultTemperament() {
        if (resultTemperament == null) {
            resultTemperament = new MutableLiveData<>();
        }
        return resultTemperament;
    }

    @SuppressLint("SetTextI18n")
    private void generateData(Image response) {
        try {
            resultName.setValue(response.getBreeds()[0].getName());
            resultCfaUrl.setValue(response.getBreeds()[0].getCfa_url());
            resultDescription.setValue(response.getBreeds()[0].getDescription());
            resultTemperament.setValue(response.getBreeds()[0].getTemperament());
        } catch (NullPointerException ignored) {}
    }

}

