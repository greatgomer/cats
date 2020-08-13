package com.example.cats.ui.image;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.R;
import com.example.cats.api.models.req.ImageVote;
import com.example.cats.api.models.res.Image;
import com.example.cats.api.services.ImageService;
import com.example.cats.databinding.ActivityImageDetailsBinding;
import com.example.cats.di.MyApplication;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageDetailViewModel extends AndroidViewModel {
    @Inject
    ImageService service;

    ActivityImageDetailsBinding binding;
    String noData = "No data";
    String id;

    public ImageDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setImage(ActivityImageDetailsBinding binding, Bundle arguments) {
        ((MyApplication) getApplication().getApplicationContext()).appComponent.votes(this);
        this.binding = binding;
        Context context = getApplication();
        String url = (String) arguments.get("url");
        id = (String) arguments.get("id");
        getImageInfo();
        onButtonsPressed();
        Picasso.with(context)
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .into(binding.fullCat);
    }

    private void onButtonsPressed() {
        View.OnClickListener down = view -> {
            ImageVote imageVote = new ImageVote(id, "test", 0);
            postVote(imageVote);
        };
        View.OnClickListener up = view -> {
            ImageVote imageVote = new ImageVote(id, "test", 1);
            postVote(imageVote);
        };
        binding.imageButtonDown.setOnClickListener(down);
        binding.imageButtonUp.setOnClickListener(up);
    }

    private void postVote(ImageVote imageVote) {
        service.imageVote(imageVote).enqueue(new Callback<ImageVote>() {
            @Override
            public void onResponse(@NotNull Call<ImageVote> call, @NotNull Response<ImageVote> response) {
                Toast.makeText(getApplication(), "Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NotNull Call<ImageVote> call, @NotNull Throwable t) {
                Toast.makeText(getApplication(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getImageInfo(){
        service.image(id).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(@NotNull Call<Image> call, @NotNull Response<Image> response) {
                generateData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<Image> call, @NotNull Throwable t) {
                Log.d("FAIL", String.valueOf(t));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void generateData(Image response) {
        try {
            binding.textViewWikipediaUrl.setText("Name: " + response.getBreeds()[0].getName());
            binding.textViewCfaUrl.setText("CfaUrl: " + response.getBreeds()[0].getCfa_url());
            binding.textViewDescription.setText("Description: " + response.getBreeds()[0].getDescription());
            binding.textViewTemperament.setText("Temperament: " + response.getBreeds()[0].getTemperament());
        } catch (NullPointerException n) {
            binding.textViewCfaUrl.setText(noData);
        }
    }

}

