package com.saifsweelam.fashionee.fragments.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saifsweelam.fashionee.CartDatabaseHelper;
import com.saifsweelam.fashionee.CartItem;
import com.saifsweelam.fashionee.CartItemsAdapter;
import com.saifsweelam.fashionee.R;

import java.util.List;

public class CartFragment extends Fragment {
    CartDatabaseHelper cartDatabaseHelper;
    RecyclerView cartRecyclerView;

    public CartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);

        cartDatabaseHelper = new CartDatabaseHelper(getContext());

        List<CartItem> cartItems = cartDatabaseHelper.getAllCartItems();

        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(cartItems);

        cartRecyclerView.setAdapter(cartItemsAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false
        ));

        return view;
    }
}