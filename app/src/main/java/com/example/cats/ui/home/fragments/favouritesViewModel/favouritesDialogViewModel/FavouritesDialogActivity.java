package com.example.cats.ui.home.fragments.favouritesViewModel.favouritesDialogViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityDialogBinding;

public class FavouritesDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDialogBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dialog);
        FavouritesDialogViewModel model = ViewModelProviders.of(this).get(FavouritesDialogViewModel.class);
        model.dialogViewModel(binding);
    }

}