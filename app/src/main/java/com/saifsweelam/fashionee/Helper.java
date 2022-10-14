package com.saifsweelam.fashionee;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Helper {

    public static void toastNetworkError(Context context) {
        Toast.makeText(
                context,
                context.getResources().getString(R.string.network_failed),
                Toast.LENGTH_LONG
        ).show();
    }

}
