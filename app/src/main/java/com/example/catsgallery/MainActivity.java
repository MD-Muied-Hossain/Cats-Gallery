package com.example.catsgallery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.catsgallery.Adapter.CatAdapter;
import com.example.catsgallery.Api.CatApi;
import com.example.catsgallery.Model.CatTableModel;
import com.example.catsgallery.Repository.CatRepository;
import com.example.catsgallery.ViewModel.CatViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private CatViewModel catViewModel;
    private List<CatTableModel> getCats;
    private CatAdapter catAdapter;
    private RecyclerView recyclerView;
    private CatRepository catRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catRepository = new CatRepository(getApplication());
        getCats = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        getSupportActionBar().hide();

        catViewModel = new ViewModelProvider(this).get(CatViewModel.class);

        catAdapter = new CatAdapter(this, getCats);
        makeRequest();
        catViewModel.getAllCats().observe(this, new Observer<List<CatTableModel>>() {
            @Override
            public void onChanged(List<CatTableModel> cat1) {
                recyclerView.setAdapter(catAdapter);
                catAdapter.getAllDatas(cat1);
                Toast.makeText(MainActivity.this, "Loading....", Toast.LENGTH_SHORT).show();
            }
        });
/*        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED )
        {
            connected = true;
        }else {
            connected = false;
        }
        if (connected = true)
        {
            catViewModel=new ViewModelProvider(this).get(CatViewModel.class);
            catAdapter=new CatAdapter(this, getCats);
            makeRequest();
            catViewModel.getAllCats().observe(this, new Observer<List<CatTableModel>>() {
                @Override
                public void onChanged(List<CatTableModel> cat1) {
                    recyclerView.setAdapter(catAdapter);
                    catAdapter.getAllDatas(cat1);
                    Toast.makeText(MainActivity.this, "Loading....", Toast.LENGTH_SHORT).show();
                }
            });
        }else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            View layout_dialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.check_internet_dialog,null);
            builder.setView(layout_dialog);
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);

            AppCompatButton btnRetry = layout_dialog.findViewById(R.id.btnRetry);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }*/
    }

    private void makeRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/images/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CatApi api = retrofit.create(CatApi.class);
        Call<List<CatTableModel>> call = api.getImgs(50);
        call.enqueue(new Callback<List<CatTableModel>>() {
            @Override
            public void onResponse(Call<List<CatTableModel>> call, Response<List<CatTableModel>> response) {
                if (response.isSuccessful()) {
                    catRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CatTableModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Himu ! Sommething went wrong Himu", Toast.LENGTH_SHORT).show();
                // Log.d("main", "onFailure: "+t.getMessage()+"Himu ! Sommething went wrong Himu");
            }
        });
    }
}