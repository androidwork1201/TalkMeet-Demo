package com.example.getobj.Interface;

import com.example.getobj.GsonData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostInterface {

    @FormUrlEncoded
    @POST("http://172.105.228.202/voxy/api/story_list.php")
    Call<GsonData> getData(
            @Header("session-token") String header,
            @Field("count") int count,
            @Field("page") int page,
            @Field("type") int type);

}