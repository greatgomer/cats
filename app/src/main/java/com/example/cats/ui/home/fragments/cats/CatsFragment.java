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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.di.MyApplication;
import com.example.cats.ui.authorisation.AuthorisationActivity;
import com.example.cats.ui.filter.FilterActivity;
import com.example.cats.R;
import com.example.cats.databinding.FragmentCatsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CatsFragment extends Fragment {
    FragmentCatsBinding binding;
    CatsFragmentViewModel model;
    private boolean loading = true;
    private int visibleThreshold = 5;
    LinearLayoutManager mLayoutManager;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    public int previousTotal = 0;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cats, container, false);
        View view = binding.getRoot();
        model = ViewModelProviders.of(this).get(CatsFragmentViewModel.class);
        model.catsViewModel(binding);
        setHasOptionsMenu(true);
        CatsFragmentViewModel.parameters.put("limit", "14");
        CatsFragmentViewModel.parameters.put("page", "0");
        addMoreCatsInFragment();

        return view;
    }

    public void addMoreCatsInFragment() {
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        binding.catRecyclerView.setLayoutManager(mLayoutManager);
        binding.catRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }

                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    model.loadCats();
                    loading = true;
                }
            }
        });
        model.loadCats();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cat_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter & !CatsFragmentViewModel.email.equals("")) {
            super.onOptionsItemSelected(item);
            Intent intent = new Intent(getContext(), FilterActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_filter & CatsFragmentViewModel.email.equals("")) {
            super.onOptionsItemSelected(item);
            Toast.makeText(getActivity(), "Add user", Toast.LENGTH_LONG).show();
            return true;
        }{
            super.onOptionsItemSelected(item);
            Intent intent = new Intent(getContext(), AuthorisationActivity.class);
            startActivity(intent);
            return true;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (FilterActivity.flag) {
            model.resultCats.clear();
            addMoreCatsInFragment();
            previousTotal = 0;
            FilterActivity.flag = false;
        }
    }

}