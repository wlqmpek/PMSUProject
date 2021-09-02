package com.example.pmsu_project.activities.sellers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.LoginActivity;
import com.example.pmsu_project.activities.RegisterActivity;
import com.example.pmsu_project.dtos.CreateArticleDTO;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.LoggedUser;
import com.example.pmsu_project.services.ArticleServices;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateArticleActivity extends AppCompatActivity {

    static final String TAG = RegisterActivity.class.getSimpleName();

    private ArticleServices articleServices;

    private Context context = this;

    private EditText name, description, price;
    private ImageView imageView;
    private Button buttonCreate;
    private Uri imageUri;
    private static final int REQUEST_CODE_SELECT_IMAGE = 0;
    private static final int REQUEST_CODE_STORAGE_PREMISSION = 1;
    private long idArtikla;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_article);

        articleServices = ApiUtils.getArticleService();

        name = findViewById(R.id.createArticleEditTextName);
        description = findViewById(R.id.createArticleEditTextMultiLineDescription);
        price = findViewById(R.id.createArticleEditTextPrice);
        imageView = findViewById(R.id.createArticleImageViewPicture);
        buttonCreate = findViewById(R.id.createArticleButtonCreate);

        // Select from gallery.
        imageView.setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateArticleActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PREMISSION);
            } else {
                selectImage();
            }
        });

        addCreateArticleButtonFunctionality();


    }

    public void addCreateArticleButtonFunctionality() {
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateArticleDTO createArticleDTO = new CreateArticleDTO(
                        name.getText().toString(),
                        description.getText().toString(),
                        Double.parseDouble(price.getText().toString())
                );
                File file = new File(imageUri.getPath());
//                showResponse(getRealPathFromURI(context, imageUri));
                RequestBody requestBody = RequestBody.create(new File(getPath(imageUri)), MediaType.parse("multipart/form-data"));
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                articleServices.saveImage(body).enqueue(new Callback<Long>() {
                    @Override public void onResponse(Call<Long> call, Response<Long> response) {
                        if(response.isSuccessful()) {
                            idArtikla = response.body();
                            articleServices.updateArticle(idArtikla, createArticleDTO).enqueue(new Callback<Article>() {
                                @Override
                                public void onResponse(Call<Article> call, Response<Article> response) {
                                    if(response.isSuccessful()) {
                                        showResponse("Success " + response.code());
                                    } else {
                                        showResponse("Kurcina " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Article> call, Throwable t) {
                                    showResponse("Kurcina " + t.toString());
                                }
                            });
                        } else {
                            showResponse("Fail x1 " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Log.e(TAG, t.toString());
                        showResponse("Fail x2 " + t);
                    }
                });
            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_STORAGE_PREMISSION && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showResponse("Granted");
                selectImage();
            } else {
                showResponse("Premmision denied");
            }
        }
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

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seller_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.myArticles) {
            Intent i = new Intent(CreateArticleActivity.this, ListSellersArticles2Activity.class);
            context.startActivity(i);
            showResponse("Sellers");
        } else if(id == R.id.createArticle) {
            Intent i = new Intent(CreateArticleActivity.this, CreateArticleActivity.class);
            context.startActivity(i);
//            showResponse("Delivered");
        } else if(id == R.id.discount) {
            showResponse("Discount");
        } else if(id == R.id.commentManagment) {
            showResponse("Comment managment");
        } else if(id == R.id.logout) {
            showResponse("Logout");
            LoggedUser.logout(this);
            Intent i = new Intent(CreateArticleActivity.this, LoginActivity.class);
            context.startActivity(i);
        }
        return true;
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
    }
}