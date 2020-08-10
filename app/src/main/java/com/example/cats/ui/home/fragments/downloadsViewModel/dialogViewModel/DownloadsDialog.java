package com.example.cats.ui.home.fragments.downloadsViewModel.dialogViewModel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    ActivityDownloadsDialogBinding binding;
    String[] name;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            uri = data.getData();
        } catch (NullPointerException n) {
            Log.d("TAG", String.valueOf(n));
        }
        File file = null;
        try {
            assert uri != null;
            file = getFile(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mimeType = "image/jpeg";
        assert file != null;
        Log.d("EXISTS", String.valueOf(file));
        assert mimeType != null;
        Log.d("mimeType", mimeType);
        Log.d("file path", file.getPath());
        Log.d("file name", file.getName());
        Log.d("uri path", String.valueOf(uri.getPath()));

        InputStream iStream = null;
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
        Log.d("FILE", String.valueOf(inputData.length));

        RequestBody requestFile =
                RequestBody.create(inputData, MediaType.parse(mimeType));

        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody subPart = createPartFromString(CatsFragmentViewModel.email);

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

    public File getFile(Uri selectedImageUri) throws IOException {
        name = Objects.requireNonNull(selectedImageUri.getPath()).split("/");
        Bitmap selectedBitmap = getBitmap(selectedImageUri);

        /*We can access getExternalFileDir() without asking any storage permission.*/
        File selectedImgFile = new File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                name[name.length-1]);

        convertBitmaptoFile(selectedImgFile, selectedBitmap);
        return selectedImgFile;
    }

    public Bitmap getBitmap(Uri imageUri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                return ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                                this.getContentResolver(),
                                imageUri));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(imageUri);
                return BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public void convertBitmaptoFile(File destinationFile, Bitmap bitmap) throws IOException {
        //create a file to write bitmap data
        destinationFile.createNewFile();
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        byte[] bitmapData = bos.toByteArray();
        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(destinationFile);
        fos.write(bitmapData);
        fos.flush();
        fos.close();
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

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}