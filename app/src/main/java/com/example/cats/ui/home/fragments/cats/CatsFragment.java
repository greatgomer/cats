package com.example.cats.ui.home.fragments.cats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.R;
import com.example.cats.databinding.FragmentCatsBinding;

import org.jetbrains.annotations.NotNull;


public class CatsFragment extends Fragment {
    private CatsAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCatsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cats, container, false);
        CatsViewModel model = ViewModelProviders.of(this).get(CatsViewModel.class);
        RecyclerView recyclerView = binding.catRecyclerView;

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CatsAdapter(getActivity());
        model.onCatsViewModel(binding);
        View view = binding.getRoot();

        model.catPagedList.observe(requireActivity(), items -> {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.submitList(items);
        });

        binding.catRecyclerView.setAdapter(adapter);

        return view;
    }

}