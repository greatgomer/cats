package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class ImageDetails extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        imageView = (ImageView) findViewById(R.id.fullCat);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        String url = (String) arguments.get("url");
        Picasso.with(this)
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }
}