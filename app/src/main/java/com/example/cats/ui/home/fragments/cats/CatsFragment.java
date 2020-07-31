package com.example.cats.ui.home.fragments.cats;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.ui.authorisation.AuthorisationActivity;
import com.example.cats.ui.filter.FilterActivity;
import com.example.cats.ui.filter.FilterActivityViewModel;
import com.example.cats.R;
import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.api.services.ImagesService;
import com.example.cats.databinding.FragmentCatsBinding;
import com.example.cats.di.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatsFragment extends Fragment {
    @Inject
    ImagesService service;

    @Inject
    FavouritesService serviceFavourites;

    private CatRecyclerAdapter adapter;
    FragmentCatsBinding binding;
    LinearLayoutManager mLayoutManager;

    public static HashMap<String, String> parameters = new HashMap<>();
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    List<Cat> resultCats = new ArrayList<>();
    static SharedPreferences sharedPreferences;
    public static String email = "";

    public CatsFragment() {}

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cats, container, false);
        View view = binding.getRoot();
        ((MyApplication) requireActivity().getApplicationContext()).appComponent.inject(this);
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity());
        addMoreCatsInFragment();
        setHasOptionsMenu(true);
        parameters.put("limit", "14");
        parameters.put("page", "0");

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cat_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        email = sharedPreferences.getString("email", "default value");
        int id = item.getItemId();
        if (id == R.id.action_filter & !email.equals("")) {
            super.onOptionsItemSelected(item);
            Intent intent = new Intent(getContext(), FilterActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_filter & email.equals("")) {
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

    public static void setEmail(String newEmail){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", newEmail);
        editor.apply();
        email = newEmail;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (FilterActivityViewModel.flag) {
            resultCats.clear();
            addMoreCatsInFragment();
            previousTotal = 0;
            FilterActivityViewModel.flag = false;
        }
    }

    private void addMoreCatsInFragment() {
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
                    loadCats();
                    loading = true;
                }
            }
        });
        loadCats();

    }

//    private void loadCats() {
//        FilterActivity filterActivity = new FilterActivity();
//        service.getAllData(filterActivity.parameters)
//                .subscribe(this::generateDataList,
//                        e -> Log.d("RETROFIT", String.valueOf(e)))
//                .isDisposed();
//    }

    private void loadCats() {
        service.getAllCats(parameters)
                .enqueue(new Callback<List<Cat>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
                        generateDataList(response.body());
                    }
                    @Override
                    public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void generateDataList(List<Cat> photoList) {
        if (resultCats.isEmpty()) {
            resultCats = new ArrayList<>(photoList);
            adapter = new CatRecyclerAdapter(serviceFavourites, getActivity(), resultCats);
            binding.catRecyclerView.setAdapter(adapter);
        } else {
            resultCats.addAll(photoList);
            adapter.notifyDataSetChanged();
        }
    }

}