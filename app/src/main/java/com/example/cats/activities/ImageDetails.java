package com.example.cats.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityImageDetailsBinding;
import com.squareup.picasso.Picasso;

public class ImageDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImageDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image_details);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        String url = (String) arguments.get("url");
        Picasso.with(this)
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .into(binding.fullCat);
    }
}