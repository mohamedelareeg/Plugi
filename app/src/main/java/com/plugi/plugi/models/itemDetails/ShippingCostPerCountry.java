package com.plugi.plugi.models.itemDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShippingCostPerCountry implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("ToCountry_ID")
    @Expose
    private Integer toCountryID;
    @SerializedName("ToCountry_Name")
    @Expose
    private String toCountryName;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("PriceCurrency")
    @Expose
    private String priceCurrency;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getToCountryID() {
        return toCountryID;
    }

    public void setToCountryID(Integer toCountryID) {
        this.toCountryID = toCountryID;
    }

    public String getToCountryName() {
        return toCountryName;
    }

    public void setToCountryName(String toCountryName) {
        this.toCountryName = toCountryName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

}
