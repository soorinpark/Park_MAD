package com.example.soorinpark.beerbelly.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soorinpark on 11/11/16.
 */

public class BreweryObject {
    @SerializedName("id")
    private String breweryId;
    @SerializedName("name")
    private String breweryName;
    @SerializedName("description")
    private String breweryDescription;

}
