package com.example.pmsu_project.activities.sellers;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.RegisterActivity;
import com.example.pmsu_project.dtos.CreateArticleDTO;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.Picture;
import com.example.pmsu_project.services.ArticleServices;
import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditArticleActivity extends AppCompatActivity {

    static final String TAG = EditArticleActivity.class.getSimpleName();
    private static final int REQUEST_CODE_SELECT_IMAGE = 0;
    private static final int REQUEST_CODE_STORAGE_PREMISSION = 1;

    private ArticleServices articleServices;
    private String picturePath;
    private Button buttonEdit;
    private ImageView imageView;
    private EditText name, description, price;
    private TextView id;
    private Article article;
    private Uri imageUri;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);
        setSupportActionBar(findViewById(R.id.createArticle));
        Intent i = getIntent();
        Long articleId = i.getLongExtra("articleId", 0);
        articleServices = ApiUtils.getArticleService();

        buttonEdit = findViewById(R.id.editArticleButtonEdit);
        imageView = findViewById(R.id.editArticleImageView);
        name = findViewById(R.id.editArticleEditTextName);
        description = findViewById(R.id.editArticleEditTextDescription);
        price = findViewById(R.id.editArticleEditTextPrice);
        id = findViewById(R.id.editArticleArticleIdTextView);


        articleServices.getArticle(articleId).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful()) {
                    article = response.body();
                    name.setText(article.getName());
                    description.setText(article.getDescription());
                    price.setText(String.valueOf(article.getPrice()));
                    id.setText(String.valueOf(article.getArticleId()));
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {

            }
        });

        articleServices.getArticlePicture(articleId).enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                if(response.isSuccessful()) {
                    Picture picture = response.body();
                    picturePath = savePictureToStorage(picture);
                    imageUri = Uri.parse(picturePath);
                    showResponse("Image uri " + imageUri);
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

        imageView.setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditArticleActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PREMISSION);
            } else {
                selectImage();
            }
        });


        addEditArticleButtonFunctionality();



    }

    private void addEditArticleButtonFunctionality() {
        buttonEdit.setOnClickListener(v -> {
            CreateArticleDTO createArticleDTO = new CreateArticleDTO(
                    name.getText().toString(),
                    description.getText().toString(),
                    Double.parseDouble(price.getText().toString())
            );
            articleServices.updateArticle(article.getArticleId(), createArticleDTO).enqueue(new Callback<Article>() {
                @Override
                public void onResponse(Call<Article> call, Response<Article> response) {
                    if(response.isSuccessful()) {
                        File file = new File(picturePath);
                        RequestBody requestBody = RequestBody.create(new File(getPath(imageUri)), MediaType.parse("multipart/form-data"));
                        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
                        articleServices.updateArticlePicture(article.getArticleId(), body);
                    } else {
                        showResponse("Fail " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Article> call, Throwable t) {
                    showResponse("Fail " + t.toString());
                }
            });
        });
    }

    public String getPath(Uri uri) {
        showResponse("Image uri x2 " + uri);
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void selectImage() {
        showResponse("select image");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        } else {
            showResponse("jebeno je null");
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                imageUri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        } else  {
            showResponse("Request code " + requestCode);
            showResponse("Result code " + resultCode + RESULT_OK + " result ok");
            showResponse("Data != null" + (data != null));
            showResponse("Data.data != null " + (data.getData() != null));
            showResponse("Something went wrong");
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