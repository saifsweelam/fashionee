package com.saifsweelam.fashionee;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.saifsweelam.fashionee.fragments.main.CategoryFragment;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> {
    List<String> tags;
    FragmentManager fragmentManager;

    public TagsAdapter(List<String> tags, FragmentManager fragmentManager) {
        this.tags = tags;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagsViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.tag_item,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull TagsViewHolder holder, int position) {
        holder.tagButton.setText(tags.get(position));
        holder.tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("categoryClicked", "hahaha");
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, CategoryFragment.newInstance(tags.get(holder.getAdapterPosition())))
                        .addToBackStack(null)
                        .commit();

                Log.d("categoryClicked", "hahaha");
            }
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    static class TagsViewHolder extends RecyclerView.ViewHolder {
        MaterialButton tagButton;

        public TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            tagButton = itemView.findViewById(R.id.tagButton);
        }
    }
}
