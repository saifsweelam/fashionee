package com.saifsweelam.fashionee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authentication {
    private final Gson gson;
    private final ApiService apiService;
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public Authentication(Context context) {
        gson = new Gson();
        apiService = RetrofitClient.getInstance().getApiService();
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void login(User user) {
        String requestBody = gson.toJson(user);
        Call<UserActionResponse> request = apiService.loginUser(requestBody);

        request.enqueue(new Callback<UserActionResponse>() {
            @Override
            public void onResponse(Call<UserActionResponse> call, Response<UserActionResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String accessToken = response.body().getAccessToken();

                    editor.putString("accessToken", accessToken);
                    editor.apply();

                    storeUserData();
                } else {
                    Toast.makeText(
                            context,
                            context.getResources().getString(R.string.login_incorrect),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<UserActionResponse> call, Throwable t) {
                toastNetworkError();
            }
        });
    }

    public void storeUserData() {
        Call<User> request = apiService.getCurrentUser(getAuthorizationHeader());

        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    assert user != null;

                    editor.putString("name", user.getName());
                    editor.putString("avatar", user.getAvatar());
                    editor.apply();
                    goToMain();
                } else {
                    toastNetworkError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                toastNetworkError();
            }
        });
    }

    public void goToMain() {
        Intent intent = new Intent(context, OnboardingActivity.class);

        context.startActivity(intent);
        ((AppCompatActivity) context).finishAffinity();
    }

    public void signup(User user) {
        String requestBody = gson.toJson(user);
        Call<User> request = apiService.createUser(requestBody);

        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User createdUser = response.body();
                    login(createdUser);
                } else {
                    toastNetworkError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                toastNetworkError();
            }
        });
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("accessToken");
        editor.remove("name");
        editor.remove("avatar");
        editor.apply();

        Intent intent = new Intent(context, StartActivity.class);

        context.startActivity(intent);
        ((AppCompatActivity) context).finishAffinity();
    }

    public String getCurrentUserName() {
        return sharedPreferences.getString("name", null);
    }

    public String getCurrentUserAvatar() {
        return sharedPreferences.getString("avatar", null);
    }

    public String getAuthorizationHeader() {
        return "Bearer " + sharedPreferences.getString("accessToken", null);
    }

    public boolean isLoggedIn() {
        String accessToken = sharedPreferences.getString("accessToken", null);

        return accessToken != null;
    }

    private void toastNetworkError() {
        Toast.makeText(
                context,
                context.getResources().getString(R.string.network_failed),
                Toast.LENGTH_LONG
        ).show();
    }
}
