package com.example.cats.ui.home.fragments.downloadsViewModel.dialogViewModel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.cats.R;
import com.example.cats.databinding.ActivityDownloadsDialogBinding;
import com.example.cats.ui.home.fragments.catsViewModel.CatsFragmentViewModel;
import com.jakewharton.rxbinding4.view.RxView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DownloadsDialog extends AppCompatActivity{
    DownloadsDialogViewModel model;
    public static final int PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    ActivityDownloadsDialogBinding binding;
    InputStream iStream = null;
    byte[] inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_downloads_dialog);
        model = ViewModelProviders.of(this).get(DownloadsDialogViewModel.class);
        model.downloadsViewModel(binding);

        RxView.clicks(binding.downloadsGallery).subscribe(aVoid -> startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE)).isDisposed();

        RxView.clicks(binding.downloadsPhoto).subscribe(aVoid -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }).isDisposed();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            assert uri != null;
            File file = new File(Objects.requireNonNull(uri.getPath()));
            try {
                iStream = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                assert iStream != null;
                inputData = model.getBytes(iStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            RequestBody requestFile =
                    RequestBody.create(inputData, MediaType.parse("image/jpeg"));

            model.filePart =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            model.subPart = createPartFromString(CatsFragmentViewModel.email);
            model.loadInDownloads();
            onBackPressed();
        } else if (requestCode == REQUEST_IMAGE_CAPTURE){
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            assert imageBitmap != null;
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            inputData = stream.toByteArray();
            imageBitmap.recycle();
            try {
                model.createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            RequestBody requestFile =
                    RequestBody.create(inputData, MediaType.parse("image/jpg"));

            model.filePart =
                    MultipartBody.Part.createFormData("file", model.currentPhotoPath, requestFile);
            model.subPart = createPartFromString(CatsFragmentViewModel.email);
            model.loadInDownloads();
            onBackPressed();
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(descriptionString,
                okhttp3.MultipartBody.FORM);
    }

}