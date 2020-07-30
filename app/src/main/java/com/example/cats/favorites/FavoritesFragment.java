package com.example.cats.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cats.R;
import com.example.cats.api.models.req.DeleteFromFavourites;
import com.example.cats.api.models.res.Favorites;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.databinding.FragmentFavoritesBinding;
import com.example.cats.di.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {

    @Inject
    FavouritesService service;

    FragmentFavoritesBinding binding;
    LinearLayoutManager mLayoutManager;
    FavoritesRecyclerAdapter adapter;
    public static List<Favorites> resultFavorites = new ArrayList<>();
    public static List<String> favouritesAllId = new ArrayList<>();

    public FavoritesFragment() {}

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        View view = binding.getRoot();
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        binding.favouritesRecyclerView.setLayoutManager(mLayoutManager);
        ((MyApplication) Objects.requireNonNull(getActivity()).getApplicationContext()).appComponent.favourites(this);
        loadFavourites();

        return view;
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
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void generateDataList(List<Favorites> photoList) {
        resultFavorites.addAll(photoList);
        adapter = new FavoritesRecyclerAdapter(getActivity(), resultFavorites);
        binding.favouritesRecyclerView.setAdapter(adapter);
    }

    public void deleteFrom(Integer imageId){
        service.deleteFromFavorites(imageId).enqueue(new Callback<DeleteFromFavourites>() {
            @Override
            public void onResponse(@NotNull Call<DeleteFromFavourites> call, @NotNull Response<DeleteFromFavourites> response) {
                Toast.makeText(getContext(), "Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NotNull Call<DeleteFromFavourites> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), "Decline", Toast.LENGTH_SHORT).show();
            }
        });
        resultFavorites.clear();
        loadFavourites();
    }

}