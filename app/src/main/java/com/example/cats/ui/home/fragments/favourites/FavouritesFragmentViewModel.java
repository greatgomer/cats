package com.example.cats.ui.home.fragments.favourites;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cats.api.models.res.Favorites;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.di.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesFragmentViewModel extends AndroidViewModel {
    @Inject
    FavouritesService service;

    @SuppressLint("StaticFieldLeak")
    Context context;

    public List<Favorites> resultFavorites = new ArrayList<>();
    public MutableLiveData<List<Favorites>> resultFavoritesList = new MutableLiveData<>();
    public static List<String> favouritesAllId = new ArrayList<>();
    SharedPreferences sharedPreferences;
    public String userName = "";

    public FavouritesFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void favouritesViewModel(){
        context = getApplication();
        ((MyApplication) getApplication().getApplicationContext()).appComponent.favourites(this);
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplication());
        userName = sharedPreferences.getString("userName", "");
        loadFavourites();
    }

    private void loadFavourites() {
        service.getAllFavorites(userName)
                .enqueue(new Callback<List<Favorites>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Favorites>> call, @NotNull Response<List<Favorites>> response) {
                        generateDataList(response.body());
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Favorites>> call, @NotNull Throwable t) {
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public LiveData<List<Favorites>> getResultFavoritesList() {
        if (resultFavoritesList == null) {
            resultFavoritesList = new MutableLiveData<>();
            loadFavourites();
        }
        return resultFavoritesList;
    }

    private void generateDataList(List<Favorites> photoList) {
        resultFavorites.addAll(photoList);
        resultFavoritesList.setValue(resultFavorites);
    }

}
