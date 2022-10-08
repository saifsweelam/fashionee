package com.saifsweelam.fashionee;

import com.google.gson.annotations.SerializedName;

public class UserActionResponse {
    private boolean isAvailable;

    @SerializedName("access_token")
    private String accessToken;

    public UserActionResponse() {}

    public UserActionResponse(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public UserActionResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
