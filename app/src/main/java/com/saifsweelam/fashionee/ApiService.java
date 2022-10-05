package com.saifsweelam.fashionee;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("users/")
    Call<User> createUser(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("users/is-available")
    Call<UserActionResponse> checkEmailAvailable(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    Call<UserActionResponse> loginUser(@Body String body);

    @GET("auth/profile")
    Call<User> getCurrentUser(@Header("Authentication") String accessToken);
}
