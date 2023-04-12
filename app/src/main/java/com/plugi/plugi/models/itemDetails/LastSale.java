package com.plugi.plugi.models.itemDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LastSale implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("TableID")
    @Expose
    private Integer tableID;
    @SerializedName("SizeValue")
    @Expose
    private String sizeValue;
    @SerializedName("PriceValue")
    @Expose
    private Double priceValue;
    @SerializedName("PriceCurrency")
    @Expose
    private String priceCurrency;
    @SerializedName("PriceDate")
    @Expose
    private String priceDate;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getTableID() {
        return tableID;
    }

    public void setTableID(Integer tableID) {
        this.tableID = tableID;
    }

    public String getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(String sizeValue) {
        this.sizeValue = sizeValue;
    }

    public Double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Double priceValue) {
        this.priceValue = priceValue;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public String getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

}