package com.example.cats.ui.home.fragments.downloadsViewModel.dialogViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.cats.R;
import com.example.cats.api.services.DownloadsService;
import com.example.cats.databinding.ActivityDownloadsDialogBinding;
import com.example.cats.di.MyApplication;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadsDialog extends AppCompatActivity {
    @Inject
    DownloadsService service;

    public static final int PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    Uri selectedImage;
    ActivityDownloadsDialogBinding binding;
    String imageName = "";
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_downloads_dialog);
        ((MyApplication) getApplication().getApplicationContext()).appComponent.downloads(this);
        DownloadsDialogViewModel model = ViewModelProviders.of(this).get(DownloadsDialogViewModel.class);
        model.downloadsViewModel(binding);
    }

    public void onDownloadsGalleryClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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
        if (requestCode == PICK_IMAGE && null != data) {
            selectedImage = data.getData();
            binding.imageView.setImageURI(selectedImage);

            File file = new File(selectedImage.getPath());
            Log.d("TAG", selectedImage.getPath());

            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",
                    file.getName(), RequestBody.create(file, MediaType.parse(String.valueOf(selectedImage))));

            service.loadImage(filePart,"test").enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("ARARARAA", String.valueOf(response));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("ADADADA", String.valueOf(t));
                }
            });
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            assert extras != null;
            imageBitmap = (Bitmap) extras.get("data");
            binding.imageView.setImageBitmap(imageBitmap);
            try {
                createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.textView.setText(imageName);
        }
    }

    public void createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(
                imageFileName,".jpg");
        // Save a file: path for use with ACTION_VIEW intents
        imageName = image.getAbsolutePath().replaceAll("[a-z/_.]", "");
        imageName = imageName + ".jpg";
    }

}