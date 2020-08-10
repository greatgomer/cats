package com.example.cats.ui.home.fragments.downloadsViewModel.dialogViewModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.cats.R;
import com.example.cats.api.services.DownloadsService;
import com.example.cats.databinding.ActivityDownloadsDialogBinding;
import com.example.cats.di.MyApplication;
import com.example.cats.ui.home.fragments.catsViewModel.CatsFragmentViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

public class DownloadsDialog extends AppCompatActivity{
    @Inject
    DownloadsService service;

    public static final int PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    String mimeType = "image/jpeg";
    ActivityDownloadsDialogBinding binding;
    InputStream iStream = null;
    MultipartBody.Part filePart;
    String currentPhotoPath;
    RequestBody subPart;
    byte[] inputData;
    Uri uri;

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
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            uri = data.getData();
            assert uri != null;
            File file = new File(Objects.requireNonNull(uri.getPath()));
            try {
                iStream = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                assert iStream != null;
                inputData = getBytes(iStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            RequestBody requestFile =
                    RequestBody.create(inputData, MediaType.parse(mimeType));

            filePart =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            subPart = createPartFromString(CatsFragmentViewModel.email);
            loadInDownloads();
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

            Log.d("SIZE", String.valueOf(inputData.length));

            try {
                createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            RequestBody requestFile =
                    RequestBody.create(inputData, MediaType.parse("image/jpg"));

            filePart =
                    MultipartBody.Part.createFormData("file", currentPhotoPath, requestFile);

            Log.d("NAME", currentPhotoPath);

            subPart = createPartFromString(CatsFragmentViewModel.email);
            loadInDownloads();
            onBackPressed();
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(descriptionString,
                okhttp3.MultipartBody.FORM);
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

    private void createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
    }

}