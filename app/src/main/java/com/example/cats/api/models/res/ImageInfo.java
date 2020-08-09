package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class ImageInfo {
    @SerializedName("cfa_url")
    private String cfa_url;
    @SerializedName("description")
    private String description;
    @SerializedName("temperament")
    private String temperament;
    @SerializedName("name")
    private String name;

    public ImageInfo(String cfa_url, String description, String temperament, String name) {
        this.cfa_url = cfa_url;
        this.description = description;
        this.temperament = temperament;
        this.name = name;
    }
    public String getCfa_url() {
        return cfa_url;
    }
    public String getDescription() {
        return description;
    }
    public String getTemperament() {
        return temperament;
    }
    public String getName() {
        return name;
    }


}
