package com.saifsweelam.fashionee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SearchView searchView;
    RecyclerView productsRecyclerView;

    ProductsViewer productsViewer;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        query = getIntent().getStringExtra("query");

        searchView = findViewById(R.id.searchView);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);

        productsViewer = new ProductsViewer(productsRecyclerView);

        productsViewer.loadSearchProducts(query, 100, null);

        searchView.setQuery(query, false);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        productsViewer.loadSearchProducts(query, 100, null);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        query = newText;
        productsViewer.loadSearchProducts(query, 100, null);
        return false;
    }
}