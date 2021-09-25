package com.example.pmsu_project.activities.buyers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.LoginActivity;
import com.example.pmsu_project.activities.RegisterActivity;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.LoggedUser;
import com.example.pmsu_project.models.Picture;
import com.example.pmsu_project.services.ArticleServices;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewArticleDetailsActivity extends AppCompatActivity {

    static final String TAG = ViewArticleDetailsActivity.class.getSimpleName();

    private Context context = this;

    private ArticleServices articleServices;

    private TextView name, price, description, onSale;
    private ImageView imageView;
    private static final int REQUEST_CODE_SELECT_IMAGE = 0;
    private static final int REQUEST_CODE_STORAGE_PREMISSION = 1;
    private Long articleId;
    private Uri imageUri;
    private String picturePath;
    private Article article;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article_details);
        setSupportActionBar(findViewById(R.id.articleDetailsToolbar));
        article = (Article) getIntent().getSerializableExtra("Article");

        articleServices = ApiUtils.getArticleService();

        name = findViewById(R.id.viewArticleTextViewName);
        price = findViewById(R.id.viewArticleTextViewPrice);
        description = findViewById(R.id.viewArticleTextViewDesc);
        description.setFocusable(false);
        onSale = findViewById(R.id.viewArticleTextViewOnSale);
        imageView = findViewById(R.id.viewArticleImageView);

        articleServices.getArticle(article.getArticleId()).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if(response.isSuccessful()) {
                    article = response.body();
                    populateFields();
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {

            }
        });

        articleServices.getArticlePicture(article.getArticleId()).enqueue(new Callback<Picture>() {
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



    private void populateFields() {
        name.setText(article.getName());
        description.setText(article.getDescription());
        price.setText(String.valueOf(article.getPrice()));
        if(article.isOnSale()) {
            onSale.setText("ON SALE!");
        } else {
            onSale.setText("NOT ON SALE!");
        }


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
                if (file.exists ()) {
                    file.delete ();
                }


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
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (!Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED)) {
                //all permissions have been granted

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buyer_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.sellers) {
            Intent i = new Intent(ViewArticleDetailsActivity.this, ListSellersActivity.class);
            startActivity(i);
        } else if(id == R.id.delivered) {
            Intent i = new Intent(ViewArticleDetailsActivity.this, ListDeliveredOrdersActivity.class);
            startActivity(i);
//            showResponse("Delivered");
        } else if(id == R.id.undelivered) {
            showResponse("Undelivered");
        } else if(id == R.id.logout) {
            LoggedUser.logout(this);
            Intent i = new Intent(ViewArticleDetailsActivity.this, LoginActivity.class);
            startActivity(i);
        }
        return true;
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }
}