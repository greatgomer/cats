package com.example.cats.ui.filterViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityFilterBinding;
import com.jakewharton.rxbinding4.view.RxView;

public class FilterActivity extends AppCompatActivity {

    public FilterActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFilterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        FilterActivityViewModel model = ViewModelProviders.of(this).get(FilterActivityViewModel.class);
        setTitle(R.string.choose_filter);

        RxView.clicks(binding.button).subscribe(aVoid -> {
            model.onButtonApplyPressed(binding);
            onBackPressed();
        }).isDisposed();

        RxView.clicks(binding.button2).subscribe(aVoid -> {
            model.onButtonResetPressed();
            onBackPressed();
        }).isDisposed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}