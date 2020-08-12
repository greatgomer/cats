package com.example.cats.ui.home.fragments.downloads.dialog;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.api.services.DownloadsService;
import com.example.cats.databinding.ActivityDownloadsDialogBinding;
import com.example.cats.di.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadsDialogViewModel extends AndroidViewModel {
    @Inject
    DownloadsService service;

    ActivityDownloadsDialogBinding binding;
    @SuppressLint("StaticFieldLeak")
    Context context;
    String currentPhotoPath;
    MultipartBody.Part filePart;
    RequestBody subPart;

    public DownloadsDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public void downloadsViewModel(ActivityDownloadsDialogBinding binding) {
        ((MyApplication) getApplication().getApplicationContext()).appComponent.download(this);
        this.binding = binding;
        context = getApplication();
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void loadInDownloads(){
        service.loadImage(filePart, subPart).enqueue(new Callback<ResponseBody>() {
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

    void createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(
                imageFileName,
                ".jpg");
        currentPhotoPath = image.getAbsolutePath();
    }

}
