package com.example.cats.favorites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cats.R;
import com.example.cats.api.models.res.Favorites;
import com.example.cats.api.services.ImagesService;
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

    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;

    List<Favorites> resultFavorites = new ArrayList<>();

    @Inject
    ImagesService service;

    public FavoritesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = view.findViewById(R.id.favouritesRecyclerView);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        ((MyApplication) Objects.requireNonNull(getActivity()).getApplicationContext()).appComponent.favorites(this);
        loadFavourites();
        return view;
    }

    private void loadFavourites(){
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
        FavoritesRecyclerAdapter adapter = new FavoritesRecyclerAdapter(getActivity(), resultFavorites);
        recyclerView.setAdapter(adapter);
    }

}