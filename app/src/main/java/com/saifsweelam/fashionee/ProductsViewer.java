package com.saifsweelam.fashionee;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsViewer implements Callback<ProductsResponse> {
    ApiService apiService;
    List<Product> products;
    RecyclerView productsRecyclerView;

    public ProductsViewer(RecyclerView productsRecyclerView) {
        this.apiService = RetrofitClient.getInstance().getApiService();
        this.productsRecyclerView = productsRecyclerView;
    }

    public void loadProducts(Integer limit, Integer skip) {
        Call<ProductsResponse> request = apiService.getProducts(limit, skip);
        request.enqueue(this);
    }

    public void loadCategoryProducts(String category, Integer limit, Integer skip) {
        Call<ProductsResponse> request = apiService.getCategoryProducts(category, limit, skip);
        request.enqueue(this);
    }

    public void loadSearchProducts(String query, Integer limit, Integer skip) {
        Call<ProductsResponse> request = apiService.searchProducts(query, limit, skip);
        request.enqueue(this);
    }

    private void displayProducts() {
        ProductsAdapter productsAdapter = new ProductsAdapter(products);
        productsRecyclerView.setAdapter(productsAdapter);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(
                productsRecyclerView.getContext(),
                2,
                LinearLayoutManager.VERTICAL,
                false
        ));
    }

    @Override
    public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
        if (response.isSuccessful()) {
            assert response.body() != null;
            products = response.body().getProducts();
            displayProducts();
        } else {
            Helper.toastNetworkError(productsRecyclerView.getContext());
        }
    }

    @Override
    public void onFailure(Call<ProductsResponse> call, Throwable t) {
        Helper.toastNetworkError(productsRecyclerView.getContext());
    }
}
