package com.saifsweelam.fashionee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    List<Product> products;

    public ProductsAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_item,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.setProduct(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setProduct(Product product) {
            ProductsViewer.displayProduct(product, view, true, true);
        }
    }
}
