package com.saifsweelam.fashionee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements Callback<Product> {
    Product product;

    ApiService apiService;

    RelativeLayout productParentView;

    int quantity = 1;

    TextView quantityView;
    MaterialButton increaseQuantityButton;
    MaterialButton decreaseQuantityButton;

    Button addToCartButton;

    TextView productPriceView;

    CartDatabaseHelper cartDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        int productId = getIntent().getIntExtra("productId", 1);

        productParentView = findViewById(R.id.productParentView);
        quantityView = findViewById(R.id.quantityView);
        increaseQuantityButton = findViewById(R.id.increaseQuantityButton);
        decreaseQuantityButton = findViewById(R.id.decreaseQuantityButton);

        productPriceView = findViewById(R.id.productPriceView);

        addToCartButton = findViewById(R.id.addToCartButton);

        apiService = RetrofitClient.getInstance().getApiService();
        cartDatabaseHelper = new CartDatabaseHelper(this);

        Call<Product> productRequest = apiService.getProduct(productId);
        productRequest.enqueue(this);
    }

    @Override
    public void onResponse(Call<Product> call, Response<Product> response) {
        if (response.isSuccessful()) {
            product = response.body();
            ProductsViewer.displayProduct(product, productParentView, false, false);

            increaseQuantityButton.setOnClickListener(v -> updateQuantity(1));
            decreaseQuantityButton.setOnClickListener(v -> updateQuantity(-1));

            addToCartButton.setOnClickListener(v -> {
                long status = cartDatabaseHelper.addCartItem(product, quantity);
                if (status != -1) {
                    Toast.makeText(
                            this,
                            getResources().getString(R.string.add_to_cart_succeed),
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    Toast.makeText(
                            this,
                            getResources().getString(R.string.add_to_cart_fail),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onFailure(Call<Product> call, Throwable t) {
        Helper.toastNetworkError(this);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void updateQuantity(int amount) {
        quantity += amount;
        if (quantity < 1) {
            quantity -= amount;
            return;
        }
        quantityView.setText(String.valueOf(quantity));
        productPriceView.setText("$" + (product.getPrice() * quantity));
    }
}