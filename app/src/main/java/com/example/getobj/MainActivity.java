package com.example.getobj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.getobj.Adapter.PostAdapter;
import com.example.getobj.databinding.ActivityMainBinding;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    PostInterface postInterface;
    PostAdapter adapter;

    String header = "xTcCQOIgNvXvJVdfTYQQDU5RMBdvI3Gb";
    int count = 10;
    int page = 1;
    int type = 6;

    String Tag = "TAG";
    ArrayList<String> imageUrl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycle.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.recycle.setHasFixedSize(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.105.228.202/voxy/api/story_list.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postInterface = retrofit.create(PostInterface.class);

        Call<GsonData> call = postInterface.getData(header, count, page, type);
        call.enqueue(new Callback<GsonData>() {
            @Override
            public void onResponse(Call<GsonData> call, Response<GsonData> response) {
                List<GsonData.DataDTO> str = response.body().getData();
                for(int i=0; i < str.size(); i++){
                    imageUrl.add(str.get(i).getUrl());
                }
                adapter = new PostAdapter(MainActivity.this, imageUrl);
                binding.recycle.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                System.out.println(imageUrl);

//                Gson gson = new Gson();
//                String s = gson.toJson(str);
//                binding.txt.setText(String.valueOf(s));
            }
            @Override
            public void onFailure(Call<GsonData> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}