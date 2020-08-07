package com.example.cats.ui.imageViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.R;
import com.example.cats.api.models.req.ImageVote;
import com.example.cats.api.models.res.Image;
import com.example.cats.api.services.ImageService;
import com.example.cats.databinding.ActivityImageDetailsBinding;
import com.example.cats.di.MyApplication;
import com.example.cats.ui.home.fragments.catsViewModel.CatsFragmentViewModel;
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
            ImageVote imageVote = new ImageVote(id, CatsFragmentViewModel.email, 0);
            postVote(imageVote);
        };
        View.OnClickListener up = view -> {
            ImageVote imageVote = new ImageVote(id, CatsFragmentViewModel.email, 1);
            postVote(imageVote);
        };
        binding.imageButtonDown.setOnClickListener(down);
        binding.imageButtonUp.setOnClickListener(up);
    }

    private void postVote(ImageVote imageVote) {
        service.imageVote(imageVote).enqueue(new Callback<ImageVote>() {
            @Override
            public void onResponse(@NotNull Call<ImageVote> call, @NotNull Response<ImageVote> response) {
                Log.d("GOOD", String.valueOf(response));
            }

            @Override
            public void onFailure(@NotNull Call<ImageVote> call, @NotNull Throwable t) {
                Log.d("BAD", String.valueOf(t));
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

    private void generateData(Image response) {
        Image image = response;
        binding.textViewDescription.setText(image.getBreeds().getDescription());
    }

}
