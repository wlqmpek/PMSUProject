package com.example.pmsu_project.activities.sellers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.RegisterActivity;
import com.example.pmsu_project.models.Picture;
import com.example.pmsu_project.services.ArticleServices;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditArticleActivity extends AppCompatActivity {

    static final String TAG = EditArticleActivity.class.getSimpleName();

    private ArticleServices articleServices;
    private String picturePath;
    private ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);
        setSupportActionBar(findViewById(R.id.createArticle));
        Intent i = getIntent();
        Long articleId = i.getLongExtra("articleId", 0);
        articleServices = ApiUtils.getArticleService();
        imageView = findViewById(R.id.editArticleImageView);
        showResponse(String.valueOf(imageView == null));

        articleServices.getArticleImage(articleId).enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                if(response.isSuccessful()) {
                    Picture picture = response.body();
                    picturePath = savePictureToStorage(picture);
                    File imgFile = new  File(picturePath);
                    if(imgFile.exists()){

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        imageView.setImageBitmap(myBitmap);

                    } else {
                        showResponse("IT DOES NOT EXIST");
                    }
                }
            }

            @Override
            public void onFailure(Call<Picture> call, Throwable t) {

            }
        });






    }

    private String savePictureToStorage(Picture picture) {
        String pathString = "";
        try {
            byte[] attaData = Base64.decode(picture.getData(), Base64.DEFAULT);
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            if(isStoragePermissionGranted()){
                File file = new File(root, picture.getName());
                if (file.exists ())
                    file.delete ();

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(attaData);
                fos.flush();
                fos.close();
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                Toast.makeText(this, "Picture saved to Downloads", Toast.LENGTH_SHORT).show();
                MediaScannerConnection.scanFile(
                        getApplicationContext(),
                        new String[]{file.getAbsolutePath()},
                        new String[]{picture.getMime_type()},
                        null);
                pathString = file.getPath();
            }

        } catch (Exception e) {
            Log.e("ERROR:", "problem pri downloadu", e);
            Toast.makeText(this, "Unable to download file", Toast.LENGTH_SHORT).show();
        }
        showResponse("Path " + pathString);
        return pathString;
    }

    public boolean isStoragePermissionGranted() {
        String TAG = "Storage Permission";
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }


//    private boolean DownlodImage(ResponseBody body) {
//        try {
//            Log.i(TAG, "Downloading img, reading and writing file");
//            InputStream in = null;
//            FileOutputStream out = null;
//
//            try {
//                in = body.byteStream();
//                out = new FileOutputStream(getExternalFilesDir(null) + File.separator + "Android ");
//            }
//        }
//    }
}