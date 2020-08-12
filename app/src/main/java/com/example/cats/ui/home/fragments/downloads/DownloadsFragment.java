package com.example.cats.ui.home.fragments.downloads;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cats.R;
import com.example.cats.databinding.FragmentDownloadsBinding;

import org.jetbrains.annotations.NotNull;

public class DownloadsFragment extends Fragment {
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDownloadsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloads, container, false);
        DownloadsViewModel model = ViewModelProviders.of(this).get(DownloadsViewModel.class);
        View view = binding.getRoot();
        model.downloadsViewModel(binding);

        return view;
    }

}