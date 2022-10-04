package com.saifsweelam.fashionee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput;
    EditText passwordInput;
    Button loginButton;

    Gson gson;
    RetrofitClient retrofitClient;
    ApiService apiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        gson = new Gson();
        retrofitClient = new RetrofitClient();
        apiService = retrofitClient.getApiService();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(emailInput.getText().toString(), passwordInput.getText().toString());

                String requestBody = gson.toJson(user);
                Log.d("userJson", requestBody);
                Call<UserActionResponse> request = apiService.loginUser(requestBody);

                request.enqueue(new Callback<UserActionResponse>() {
                    @Override
                    public void onResponse(Call<UserActionResponse> call, Response<UserActionResponse> response) {
                        Log.d("userJson", response.raw().toString());
                    }

                    @Override
                    public void onFailure(Call<UserActionResponse> call, Throwable t) {
                        Log.e("userJson", t.toString());
                    }
                });
            }
        });
    }
}