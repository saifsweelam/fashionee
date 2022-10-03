package com.saifsweelam.fashionee;

public class OnboardingItem {
    private String heading;
    private String description;
    private int imageResource;

    public OnboardingItem(String heading, String description, int imageResource) {
        this.heading = heading;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
