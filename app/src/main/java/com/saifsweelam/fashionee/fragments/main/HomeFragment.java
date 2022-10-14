package com.saifsweelam.fashionee.fragments.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saifsweelam.fashionee.ApiService;
import com.saifsweelam.fashionee.Helper;
import com.saifsweelam.fashionee.Product;
import com.saifsweelam.fashionee.ProductsViewer;
import com.saifsweelam.fashionee.R;
import com.saifsweelam.fashionee.RetrofitClient;
import com.saifsweelam.fashionee.TagsAdapter;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ApiService apiService;
    private TagsAdapter categoriesAdapter;

    private List<String> categories;

    private ImageView sampleProductImageView;
    private TextView sampleProductTitleView;
    private TextView sampleProductDescriptionView;
    private TextView sampleProductPriceView;
    private RecyclerView categoriesRecyclerView;
    private RecyclerView topProductsRecyclerView;

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        topProductsRecyclerView = view.findViewById(R.id.topProductsRecyclerView);

        sampleProductImageView = view.findViewById(R.id.sampleProductImageView);
        sampleProductTitleView = view.findViewById(R.id.sampleProductTitleView);
        sampleProductDescriptionView = view.findViewById(R.id.sampleProductDescriptionView);
        sampleProductPriceView = view.findViewById(R.id.sampleProductPriceView);

        apiService = RetrofitClient.getInstance().getApiService();

        ProductsViewer productsViewer = new ProductsViewer(topProductsRecyclerView);
        productsViewer.loadProducts(20, 33);

        displayCategories();

        displaySampleProduct();

        return view;
    }

    private void displayCategories() {
        Call<List<String>> categoriesRequest = apiService.getCategories();

        categoriesRequest.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Log.d("categoriesResponse", response.toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.d("categoriesResponse", response.body().toString());
                    categories = response.body();
                    categoriesAdapter = new TagsAdapter(categories);
                    categoriesRecyclerView.setAdapter(categoriesAdapter);
                    categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(
                            getContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                    ));
                } else {
                    Helper.toastNetworkError(getContext());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("categoriesResponse", t.toString());
                Helper.toastNetworkError(getContext());
            }
        });
    }

    private void displaySampleProduct() {
        int randomIndex = ThreadLocalRandom.current().nextInt(1, 99);
        Call<Product> productRequest = apiService.getProduct(randomIndex);

        productRequest.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();

                    assert product != null;
                    sampleProductTitleView.setText(product.getTitle());
                    sampleProductDescriptionView.setText(product.getDescription().substring(0, 30));
                    sampleProductPriceView.setText("$"+product.getPrice());

                    Glide.with(getContext())
                            .load(product.getThumbnail())
                            .centerCrop()
                            .into(sampleProductImageView);
                } else {
                    displaySampleProduct();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Helper.toastNetworkError(getContext());
            }
        });
    }
}