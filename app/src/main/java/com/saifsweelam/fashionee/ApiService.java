package com.saifsweelam.fashionee;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String alternativeBaseUrl = "https://dummyjson.com/";

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
    Call<User> getCurrentUser(@Header("Authorization") String authorizationHeader);

    @GET(alternativeBaseUrl + "products/categories")
    Call<List<String>> getCategories();

    @GET(alternativeBaseUrl + "products/{id}")
    Call<Product> getProduct(@Path("id") int productId);

    @GET(alternativeBaseUrl + "products")
    Call<ProductsResponse> getProducts(@Query("limit") Integer limit, @Query("skip") Integer skip);

    @GET(alternativeBaseUrl + "products/category/{category}")
    Call<ProductsResponse> getCategoryProducts(@Path("category") String category, @Query("limit") Integer limit, @Query("skip") Integer skip);

    @GET(alternativeBaseUrl + "products/search")
    Call<ProductsResponse> searchProducts(@Query("q") String query, @Query("limit") Integer limit, @Query("skip") Integer skip);
}
