package com.saifsweelam.fashionee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ProductImagesViewHolder> {
    List<String> imageUrls;

    public ProductImagesAdapter(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ProductImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductImagesViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_image_item,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImagesViewHolder holder, int position) {
        holder.setProductImageUrl(imageUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    static class ProductImagesViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImageView;
        public ProductImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
        }

        public void setProductImageUrl(String url) {
            Glide.with(productImageView.getContext())
                    .load(url)
                    .centerCrop()
                    .into(productImageView);
        }
    }
}
