package com.example.getobj.Client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String THE_URL = "http://172.105.228.202/voxy/api/story_list.php/";

    private static Retrofit retrofit;

    public static Retrofit getClientData(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(THE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
