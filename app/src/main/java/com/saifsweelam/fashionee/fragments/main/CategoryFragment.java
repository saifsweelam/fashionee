package com.saifsweelam.fashionee.fragments.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saifsweelam.fashionee.ProductsViewer;
import com.saifsweelam.fashionee.R;

public class CategoryFragment extends Fragment {
    private String category;

    TextView categoryNameView;
    RecyclerView productsRecyclerView;

    public CategoryFragment() {}

    public static CategoryFragment newInstance(String category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        categoryNameView = view.findViewById(R.id.categoryNameView);
        productsRecyclerView = view.findViewById(R.id.productsRecyclerView);

        ProductsViewer productsViewer = new ProductsViewer(productsRecyclerView);

        categoryNameView.setText(category);
        productsViewer.loadCategoryProducts(category, 20, null);

        return view;
    }
}