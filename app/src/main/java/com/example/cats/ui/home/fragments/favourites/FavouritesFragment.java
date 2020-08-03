package com.example.cats.ui.home.fragments.favourites;

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

public class FavouritesFragment extends Fragment {

    @Inject
    FavouritesService service;

    FragmentFavoritesBinding binding;
    LinearLayoutManager mLayoutManager;
    FavouritesRecyclerAdapter adapter;
    public List<Favorites> resultFavorites = new ArrayList<>();
    public static List<String> favouritesAllId = new ArrayList<>();

    public FavouritesFragment() {}

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        View view = binding.getRoot();
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        binding.favouritesRecyclerView.setLayoutManager(mLayoutManager);
        ((MyApplication) requireActivity().getApplicationContext()).appComponent.favourites(this);
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
        adapter = new FavouritesRecyclerAdapter(service, getActivity(), resultFavorites);
        binding.favouritesRecyclerView.setAdapter(adapter);
    }

}