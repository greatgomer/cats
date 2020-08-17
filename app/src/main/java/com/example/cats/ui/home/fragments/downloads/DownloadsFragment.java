package com.example.cats.ui.home.fragments.downloads;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cats.R;
import com.example.cats.databinding.FragmentDownloadsBinding;
import com.example.cats.ui.home.fragments.downloads.dialog.DownloadsDialog;
import com.example.cats.ui.home.fragments.favourites.FavouritesRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

public class DownloadsFragment extends Fragment {
    LinearLayoutManager mLayoutManager;
    FragmentDownloadsBinding binding;

    DownloadsFragmentAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloads, container, false);
        DownloadsViewModel model = ViewModelProviders.of(this).get(DownloadsViewModel.class);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        binding.downloadsRecyclerView.setLayoutManager(mLayoutManager);
        View view = binding.getRoot();
        model.downloadsViewModel(binding);
        model.getResultDownloadsList().observe(requireActivity(), list ->{
            adapter = new DownloadsFragmentAdapter(getActivity(), list);
            binding.downloadsRecyclerView.setAdapter(adapter);
        });
        onFabClick();

        return view;
    }

    public void onFabClick(){
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(requireActivity(), DownloadsDialog.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            requireActivity().startActivity(intent);
        });
    }

}