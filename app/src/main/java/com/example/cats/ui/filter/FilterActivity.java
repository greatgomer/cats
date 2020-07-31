package com.example.cats.ui.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityFilterBinding;

public class FilterActivity extends AppCompatActivity {

    public FilterActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFilterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        FilterActivityViewModel model = ViewModelProviders.of(this).get(FilterActivityViewModel.class);
        setTitle(R.string.choose_filter);
        model.onButtonPressed(binding);
    }

}