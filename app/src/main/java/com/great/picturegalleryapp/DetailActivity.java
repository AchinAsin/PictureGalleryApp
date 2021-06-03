package com.great.picturegalleryapp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 100;
    ImageView imageView;
    TextView textView;
    String name;
    String imageUrl;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.imageView_Full);
        textView = findViewById(R.id.textView_Name);


        /*String name = getIntent().getExtras().getString("title");*/

        name= getIntent().getExtras().getString("title");
        imageUrl= getIntent().getExtras().getString("url_s");
        String FileName = "File Name: " + name;
        Glide.with(this).load(imageUrl)
                .into(imageView);
        textView.setText(FileName);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.saveFileIcon:
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                    }
                    imageUrl= getIntent().getExtras().getString("title");
                    name= getIntent().getExtras().getString("url_s");
                    downloadImage(imageUrl,name);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void downloadImage(String url, String filename)
    {

        name= getIntent().getExtras().getString("title");
        imageUrl= getIntent().getExtras().getString("url_s");
        /*String picture_url = getIntent().getExtras().getString("url_s");*/
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(imageUrl));
        request.setTitle(name);
        request.setDescription("Downloading "+name);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,name);
        DownloadManager downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

}
