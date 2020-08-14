package com.example.cats.ui.home.fragments.cats.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityFilterBinding;
import com.example.cats.ui.home.fragments.cats.CatDataSource;
import com.jakewharton.rxbinding4.view.RxView;

public class FilterActivity extends AppCompatActivity {
    ActivityFilterBinding binding;
    public static boolean flag = false;

    public FilterActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
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
            CatDataSource.parameters.put("category_ids", "5");
            flag = true;
        }
        if (binding.filterClothes.isChecked()) {
            CatDataSource.parameters.put("category_ids", "15");
            flag = true;
        }
        if (binding.filterHats.isChecked()) {
            CatDataSource.parameters.put("category_ids", "1");
            flag = true;
        }
        if (binding.filterSinks.isChecked()) {
            CatDataSource.parameters.put("category_ids", "14");
            flag = true;
        }
        if (binding.filterSpace.isChecked()) {
            CatDataSource.parameters.put("category_ids", "2");
            flag = true;
        }
        if (binding.filterSunglasses.isChecked()) {
            CatDataSource.parameters.put("category_ids", "4");
            flag = true;
        }
        if (binding.filterTies.isChecked()) {
            CatDataSource.parameters.put("category_ids", "7");
            flag = true;
        }
    }

    public void onButtonResetPressed() {
        CatDataSource.parameters.clear();
        CatDataSource.parameters.put("limit", "14");
        CatDataSource.parameters.put("page", "0");
        flag = true;
    }

}