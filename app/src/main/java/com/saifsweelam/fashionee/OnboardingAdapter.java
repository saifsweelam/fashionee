package com.saifsweelam.fashionee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {
    private final ArrayList<OnboardingItem> onboardingItems;

    public OnboardingAdapter(ArrayList<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingAdapter.OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.onboarding_page_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingAdapter.OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private final TextView headingView;
        private final TextView descriptionView;
        private final OnboardingImageView imageView;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            headingView = (TextView) itemView.findViewById(R.id.onboardingHeading);
            descriptionView = (TextView) itemView.findViewById(R.id.onboardingDescription);
            imageView = (OnboardingImageView) itemView.findViewById(R.id.onboardingImage);
        }

        public void setOnboardingData(OnboardingItem item) {
            headingView.setText(item.getHeading());
            descriptionView.setText(item.getDescription());
            imageView.setImageResource(item.getImageResource());
        }
    }
}
