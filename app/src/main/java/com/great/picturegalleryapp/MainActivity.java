package com.great.picturegalleryapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<Photo> arrayList;
    private RecyclerView recyclerView;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        arrayList=new ArrayList<>();
        swipeContainer=findViewById(R.id.swipeContainer);


        initViews();

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                initViews();
                /*Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Pictures");
        pd.setCancelable(false);
        pd.show();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        FlickerApi();
    }


    private void FlickerApi() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<PojoClass> call = apiInterface.GetListResources();
        call.enqueue(new Callback<PojoClass>() {
            @Override
            public void onResponse(Call<PojoClass> call, Response<PojoClass> response) {
                Log.e("On Response", "Response Message: " + response.code());
                /*assert response.body() != null;*/
                assert response.body() != null;
                Photos total=response.body().getPhotos();
                assert total != null;
                List<Photos.Photo> photos= total.getPhoto();

                for(Photos.Photo photo1:photos)
                {
                    Log.e("On Response","ResponseMessage: emails: "+photo1.getUrlS());
                    /*Toast.makeText(MainActivity.this, "Url: "+photo1.getUrlS(), Toast.LENGTH_SHORT).show();*/
                    arrayList.add(new Photo(photo1.getUrlS(),photo1.getTitle()));
                    ItemAdapter recyclerAdapter=new ItemAdapter(arrayList, MainActivity.this);
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

            }
            @Override
            public void onFailure(Call<PojoClass> call, Throwable t) {
                Log.e("On Response", "Response Error Message: " + t.getMessage());
                pd.hide();
            }
        });
    }
}