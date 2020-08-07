package com.example.cats.ui.home.fragments.downloadsViewModel.dialogViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.cats.R;
import com.example.cats.api.services.DownloadsService;
import com.example.cats.databinding.ActivityDownloadsDialogBinding;

import javax.inject.Inject;

public class DownloadsDialog extends AppCompatActivity{
    @Inject
    DownloadsService service;

    public static final int PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    ActivityDownloadsDialogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_downloads_dialog);
        DownloadsDialogViewModel model = ViewModelProviders.of(this).get(DownloadsDialogViewModel.class);
        model.downloadsViewModel(binding);
    }

    public void onDownloadsGalleryClick(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
    }

    public void onCameraGalleryClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}