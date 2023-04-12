package com.plugi.plugi.models.itemDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestrictedCountriesToBuyItem implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CountryID")
    @Expose
    private Integer countryID;
    @SerializedName("HasBuy")
    @Expose
    private Boolean hasBuy;
    @SerializedName("HasSell")
    @Expose
    private Boolean hasSell;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public Boolean getHasBuy() {
        return hasBuy;
    }

    public void setHasBuy(Boolean hasBuy) {
        this.hasBuy = hasBuy;
    }

    public Boolean getHasSell() {
        return hasSell;
    }

    public void setHasSell(Boolean hasSell) {
        this.hasSell = hasSell;
    }

}
