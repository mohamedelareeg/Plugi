package com.plugi.plugi.models.orderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistoryList implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Item_ID")
    @Expose
    private Integer itemID;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
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
    @SerializedName("StatusName")
    @Expose
    private String statusName;

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

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
