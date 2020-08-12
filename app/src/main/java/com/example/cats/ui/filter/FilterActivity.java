package com.example.cats.ui.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityFilterBinding;
import com.example.cats.ui.home.fragments.cats.CatsFragmentViewModel;
import com.jakewharton.rxbinding4.view.RxView;

public class FilterActivity extends AppCompatActivity {
    ActivityFilterBinding binding;
    FilterActivityViewModel model;
    public static boolean flag = false;

    public FilterActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        model = ViewModelProviders.of(this).get(FilterActivityViewModel.class);
        setTitle(R.string.choose_filter);

        RxView.clicks(binding.applyFilter).subscribe(aVoid -> {
            onButtonApplyPressed();
            onBackPressed();
        }).isDisposed();

        RxView.clicks(binding.resetFilter).subscribe(aVoid -> {
            onButtonResetPressed();
            onBackPressed();
        }).isDisposed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onButtonApplyPressed() {
        if (binding.filterBoxes.isChecked()) {
            CatsFragmentViewModel.parameters.put("category_ids", "5");
            flag = true;
        }
        if (binding.filterClothes.isChecked()) {
            CatsFragmentViewModel.parameters.put("category_ids", "15");
            flag = true;
        }
        if (binding.filterHats.isChecked()) {
            CatsFragmentViewModel.parameters.put("category_ids", "1");
            flag = true;
        }
        if (binding.filterSinks.isChecked()) {
            CatsFragmentViewModel.parameters.put("category_ids", "14");
            flag = true;
        }
        if (binding.filterSpace.isChecked()) {
            CatsFragmentViewModel.parameters.put("category_ids", "2");
            flag = true;
        }
        if (binding.filterSunglasses.isChecked()) {
            CatsFragmentViewModel.parameters.put("category_ids", "4");
            flag = true;
        }
        if (binding.filterTies.isChecked()) {
            CatsFragmentViewModel.parameters.put("category_ids", "7");
            flag = true;
        }
    }

    public void onButtonResetPressed() {
        model.onButtonPressed();
        flag = true;
    }

}