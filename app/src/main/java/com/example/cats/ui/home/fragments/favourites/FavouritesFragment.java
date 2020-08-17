package com.example.cats.ui.home.fragments.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cats.R;
import com.example.cats.databinding.FragmentFavoritesBinding;

import org.jetbrains.annotations.NotNull;

public class FavouritesFragment extends Fragment {
    FavouritesRecyclerAdapter adapter;
    LinearLayoutManager mLayoutManager;

    public FavouritesFragment() {}

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFavoritesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        FavouritesFragmentViewModel model = ViewModelProviders.of(this).get(FavouritesFragmentViewModel.class);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        binding.favouritesRecyclerView.setLayoutManager(mLayoutManager);
        View view = binding.getRoot();
        model.favouritesViewModel();
        model.getResultFavoritesList().observe(requireActivity(), list ->{
            adapter = new FavouritesRecyclerAdapter(getActivity(), list);
            binding.favouritesRecyclerView.setAdapter(adapter);
        });

        return view;
    }

}