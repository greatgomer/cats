package com.example.cats.ui.home.fragments.favouritesViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cats.api.models.res.Favorites;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.databinding.FragmentFavoritesBinding;
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
    LinearLayoutManager mLayoutManager;
    FragmentFavoritesBinding binding;

    FavouritesRecyclerAdapter adapter;
    public List<Favorites> resultFavorites = new ArrayList<>();
    public static List<String> favouritesAllId = new ArrayList<>();

    public FavouritesFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void favouritesViewModel(FragmentFavoritesBinding binding){
        this.binding = binding;
        context = getApplication();
        mLayoutManager = new GridLayoutManager(context, 2);
        binding.favouritesRecyclerView.setLayoutManager(mLayoutManager);
        ((MyApplication) getApplication().getApplicationContext()).appComponent.favourites(this);
        loadFavourites();

    }

    private void loadFavourites() {
        service.getAllFavorites()
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

    private void generateDataList(List<Favorites> photoList) {
        resultFavorites.addAll(photoList);
        adapter = new FavouritesRecyclerAdapter(context, resultFavorites);
        binding.favouritesRecyclerView.setAdapter(adapter);
    }
}
