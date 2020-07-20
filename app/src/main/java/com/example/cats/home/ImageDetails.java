package com.example.cats.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.cats.R;
import com.example.cats.databinding.ActivityImageDetailsBinding;
import com.squareup.picasso.Picasso;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ImageDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImageDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image_details);

        Bundle arguments = getIntent().getExtras();
        String url = (String) arguments.get("url");
        Picasso.with(this)
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .into(binding.fullCat);
    }
}