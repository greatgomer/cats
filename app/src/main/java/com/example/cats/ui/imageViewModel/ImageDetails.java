package com.example.cats.ui.imageViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityImageDetailsBinding;

public class ImageDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImageDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image_details);
        ImageDetailViewModel model = ViewModelProviders.of(this).get(ImageDetailViewModel.class);
        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        model.setImage(binding, arguments);
    }

}