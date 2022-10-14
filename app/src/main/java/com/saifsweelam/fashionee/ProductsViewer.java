package com.saifsweelam.fashionee;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

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



    public static void displayProduct(Product product, View parentView, boolean shortDescription, boolean isClickable) {
        ImageView productImageView = parentView.findViewById(R.id.productImageView);
        TextView productTitleView = parentView.findViewById(R.id.productTitleView);
        TextView productDescriptionView = parentView.findViewById(R.id.productDescriptionView);
        TextView productPriceView = parentView.findViewById(R.id.productPriceView);
        RatingBar productRatingView = parentView.findViewById(R.id.productRatingView);
        ViewPager2 productImagesViewPager = parentView.findViewById(R.id.productImagesViewPager);
        DotsIndicator productImagesIndicator = parentView.findViewById(R.id.productImagesIndicator);
        MaterialButton productOpenButton = parentView.findViewById(R.id.productOpenButton);

//        View.OnClickListener onClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openProduct(parentView.getContext(), product.getId());
//            }
//        };

        if (productTitleView != null) productTitleView.setText(product.getTitle());
        if (productDescriptionView != null) {
            String description = product.getDescription();
            productDescriptionView.setText(
                    shortDescription ? description.substring(0, Math.min(40, description.length())) : description
            );
        }
        if (productPriceView != null) productPriceView.setText("$"+product.getPrice());
        if (productRatingView != null) productRatingView.setRating((float) (product.getRating()+0f));
        if (productImageView != null) {
            Glide.with(parentView.getContext())
                    .load(product.getThumbnail())
                    .centerCrop()
                    .into(productImageView);
        }
        if (productImagesViewPager != null) {
            ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(product.getImages());
            productImagesViewPager.setAdapter(productImagesAdapter);
            productImagesIndicator.setViewPager2(productImagesViewPager);
        }
//        if (productOpenButton != null) productOpenButton.setOnClickListener(onClickListener);
//        if (isClickable) parentView.setOnClickListener(onClickListener);
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
