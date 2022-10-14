package com.saifsweelam.fashionee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemsViewHolder> {
    List<CartItem> cartItems;

    public CartItemsAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cart_item,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsViewHolder holder, int position) {
        holder.setCartItem(cartItems.get(position));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartItemsViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView quantityView;
        private final TextView productPriceView;

        public CartItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            quantityView = itemView.findViewById(R.id.quantityView);
            productPriceView = itemView.findViewById(R.id.productPriceView);
        }

        public void setCartItem(CartItem cartItem) {
            ProductsViewer.displayProduct(cartItem.getProduct(), view, false, false);
            quantityView.setText(
                    cartItem.getQuantity() + " " + view.getContext().getResources().getString(R.string.pieces)
            );
            productPriceView.setText("$" + (cartItem.getProduct().getPrice() * cartItem.getQuantity()));
        }
    }
}
