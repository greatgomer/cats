package com.example.cats.ui.imageViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.R;
import com.example.cats.databinding.ActivityImageDetailsBinding;
import com.squareup.picasso.Picasso;

public class ImageDetailViewModel extends AndroidViewModel {
    public ImageDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setImage(ActivityImageDetailsBinding binding, Bundle arguments){
        Context context = getApplication();
        String url = (String) arguments.get("url");
        Picasso.with(context)
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .into(binding.fullCat);
    }
}
