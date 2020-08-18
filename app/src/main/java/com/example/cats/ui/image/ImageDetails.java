package com.example.cats.ui.image;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.cats.R;
import com.example.cats.databinding.ActivityImageDetailsBinding;
import com.jakewharton.rxbinding4.view.RxView;

public class ImageDetails extends AppCompatActivity {
    ActivityImageDetailsBinding binding;
    ImageDetailViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_details);
        model = ViewModelProviders.of(this).get(ImageDetailViewModel.class);
        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        model.setImage(binding, arguments);
        contentImageDetails();

    }

    @SuppressLint("SetTextI18n")
    public void contentImageDetails() {
        RxView.clicks(binding.imageButtonUp).subscribe(aVoid -> model.postVote()).isDisposed();
        model.getResultName().observe(this, name -> binding.textViewWikipediaUrl.setText("Name: " + name));
        model.getResultCfaUrl().observe(this, cfa -> binding.textViewCfaUrl.setText("Name: " + cfa));
        model.getResultDescription().observe(this, description -> binding.textViewDescription.setText("Name: " + description));
        model.getResultTemperament().observe(this, temperament -> binding.textViewTemperament.setText("Name: " + temperament));
    }

}