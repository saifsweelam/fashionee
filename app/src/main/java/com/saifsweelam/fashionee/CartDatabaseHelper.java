package com.saifsweelam.fashionee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CartDatabaseHelper extends SQLiteOpenHelper {
    private Gson gson;
    private static final String DATABASE_NAME = "fashionee_local_data";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "cart_items";
    private static final String ID_COL = "id";
    private static final String PRODUCT_COL = "product";
    private static final String QUANTITY_COL = "quantity";


    public CartDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        gson = new Gson();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PRODUCT_COL + " TEXT NOT NULL," +
                QUANTITY_COL + " INTEGER)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addCartItem(Product product, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("quantity", quantity);
        values.put("product", gson.toJson(product));

        long status = db.insert(TABLE_NAME, null, values);
        db.close();

        return status;
    }

    public List<CartItem> getAllCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL));
                Product product = gson.fromJson(cursor.getString(
                        cursor.getColumnIndexOrThrow(PRODUCT_COL)),
                        Product.class
                );
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(QUANTITY_COL));
                cartItems.add(new CartItem(id, product, quantity));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cartItems;
    }
}
