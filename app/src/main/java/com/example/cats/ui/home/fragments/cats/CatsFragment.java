package com.example.cats.ui.home.fragments.cats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.R;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.databinding.FragmentCatsBinding;
import com.example.cats.di.MyApplication;
import com.example.cats.ui.home.MainActivity;
import com.example.cats.ui.home.fragments.cats.authorisation.AuthorisationActivity;
import com.example.cats.ui.home.fragments.cats.filter.FilterActivity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;


public class CatsFragment extends Fragment {
    @Inject
    FavouritesService service;

    private CatsAdapter adapter;
    CatsViewModel model;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCatsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cats, container, false);
        ((MyApplication) getActivity().getApplication().getApplicationContext()).appComponent.favourite(this);
        model = ViewModelProviders.of(this).get(CatsViewModel.class);
        RecyclerView recyclerView = binding.catRecyclerView;
        setHasOptionsMenu(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CatsAdapter(service, getActivity());
        model.onCatsViewModel(binding);
        View view = binding.getRoot();

        model.catPagedList.observe(requireActivity(), items -> {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.submitList(items);
        });

        binding.catRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cat_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_authorisation) {
            super.onOptionsItemSelected(item);
            Intent intent = new Intent(getContext(), AuthorisationActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_filter & !AuthorisationActivity.userName.equals("")) {
            super.onOptionsItemSelected(item);
            Intent intent = new Intent(getContext(), FilterActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Add user", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (FilterActivity.flag) {
            MainActivity.navController.navigate(R.id.catsFragment);
            FilterActivity.flag = false;
        }
    }

}