package com.saifsweelam.fashionee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText passwordConfirmInput;
    Button signupButton;

    Gson gson;
    RetrofitClient retrofitClient;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordConfirmInput = findViewById(R.id.passwordConfirmInput);
        signupButton = findViewById(R.id.signupButton);

        gson = new Gson();
        retrofitClient = new RetrofitClient();
        apiService = retrofitClient.getApiService();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String passwordConfirm = passwordConfirmInput.getText().toString();

                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(
                            SignupActivity.this,
                            getResources().getString(R.string.passwords_dont_match),
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                User user = new User(email, password, name);
                String requestBody = gson.toJson(user);
                Log.d("userJson", requestBody);

                Call<User> request = apiService.createUser(requestBody);

                request.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("userJson", response.toString());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("userJson", t.toString());
                    }
                });
            }
        });
    }
}