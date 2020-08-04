package com.example.cats.ui.home.fragments.downloadsViewModel;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cats.R;
import com.example.cats.databinding.FragmentDownloadsBinding;

import org.jetbrains.annotations.NotNull;

public class DownloadsFragment extends Fragment {

    public DownloadsFragment() {}

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDownloadsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloads, container, false);
        View view = binding.getRoot();

        return view;
    }

}