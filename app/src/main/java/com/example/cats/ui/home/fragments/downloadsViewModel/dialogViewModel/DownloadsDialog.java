package com.example.cats.ui.home.fragments.downloadsViewModel.dialogViewModel;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.cats.R;
import com.example.cats.api.services.DownloadsService;
import com.example.cats.databinding.ActivityDownloadsDialogBinding;
import com.example.cats.di.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        ((MyApplication) getApplication().getApplicationContext()).appComponent.download(this);
        DownloadsDialogViewModel model = ViewModelProviders.of(this).get(DownloadsDialogViewModel.class);
        model.downloadsViewModel(binding);
        binding.downloadsGallery.setOnClickListener(gallery);
        binding.downloadsPhoto.setOnClickListener(camera);
    }

    View.OnClickListener gallery = view ->
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);

    View.OnClickListener camera = view -> {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        File file = new File(this.getFilesDir() + getPath(uri));
        file.mkdir();
        Log.d("EXISTS", String.valueOf(file.exists()));

        RequestBody requestFile =
                RequestBody.create(file.getPath(), MediaType.parse("multipart/form-data"));

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        service.loadImage(body, "test").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                Log.d("TAG", String.valueOf(response));
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Log.d("TAG2", String.valueOf(t));
            }
        });
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media._ID };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
}