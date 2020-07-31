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

import com.example.cats.ui.authorisation.AuthorisationActivity;
import com.example.cats.ui.filter.FilterActivity;
import com.example.cats.ui.filter.FilterActivityViewModel;
import com.example.cats.R;
import com.example.cats.databinding.FragmentCatsBinding;

import org.jetbrains.annotations.NotNull;

public class CatsFragment extends Fragment {
    FragmentCatsBinding binding;
    CatsFragmentViewModel model;


    public CatsFragment() {}

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
    public void onResume() {
        super.onResume();
        if (FilterActivityViewModel.flag) {
            model.resultCats.clear();
            model.addMoreCatsInFragment();
            model.previousTotal = 0;
            FilterActivityViewModel.flag = false;
        }
    }

}