package com.saifsweelam.fashionee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> {
    List<String> tags;

    public TagsAdapter(List<String> tags) {
        this.tags = tags;
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
