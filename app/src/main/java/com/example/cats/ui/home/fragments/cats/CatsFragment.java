package com.example.cats.ui.home.fragments.cats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cats.R;
import com.example.cats.databinding.FragmentCatsBinding;

import org.jetbrains.annotations.NotNull;

public class CatsFragment extends Fragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCatsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cats, container, false);
        CatsViewModel model = ViewModelProviders.of(this).get(CatsViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

}