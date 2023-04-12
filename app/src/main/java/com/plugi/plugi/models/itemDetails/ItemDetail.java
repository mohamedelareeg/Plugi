package com.plugi.plugi.models.itemDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemDetail implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ItemID")
    @Expose
    private Integer itemID;
    @SerializedName("MainImage")
    @Expose
    private String mainImage;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ConditionName")
    @Expose
    private String conditionName;
    @SerializedName("MaxBidPrice")
    @Expose
    private Double maxBidPrice;
    @SerializedName("LowAskPrice")
    @Expose
    private Double lowAskPrice;
    @SerializedName("PriceCurrency")
    @Expose
    private String priceCurrency;
    @SerializedName("LastSalePrice")
    @Expose
    private Double lastSalePrice;
    @SerializedName("LastSaleDate")
    @Expose
    private String lastSaleDate;
    @SerializedName("LastSaleDesc")
    @Expose
    private String lastSaleDesc;
    @SerializedName("LastSalePriceFlgUpDown")
    @Expose
    private Integer lastSalePriceFlgUpDown;
    @SerializedName("TotalSold")
    @Expose
    private Integer totalSold;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("BrandId")
    @Expose
    private Integer brandId;
    @SerializedName("BrandName")
    @Expose
    private String brandName;
    @SerializedName("ViewURL")
    @Expose
    private String viewURL;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public Double getMaxBidPrice() {
        return maxBidPrice;
    }

    public void setMaxBidPrice(Double maxBidPrice) {
        this.maxBidPrice = maxBidPrice;
    }

    public Double getLowAskPrice() {
        return lowAskPrice;
    }

    public void setLowAskPrice(Double lowAskPrice) {
        this.lowAskPrice = lowAskPrice;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public Double getLastSalePrice() {
        return lastSalePrice;
    }

    public void setLastSalePrice(Double lastSalePrice) {
        this.lastSalePrice = lastSalePrice;
    }

    public String getLastSaleDate() {
        return lastSaleDate;
    }

    public void setLastSaleDate(String lastSaleDate) {
        this.lastSaleDate = lastSaleDate;
    }

    public String getLastSaleDesc() {
        return lastSaleDesc;
    }

    public void setLastSaleDesc(String lastSaleDesc) {
        this.lastSaleDesc = lastSaleDesc;
    }

    public Integer getLastSalePriceFlgUpDown() {
        return lastSalePriceFlgUpDown;
    }

    public void setLastSalePriceFlgUpDown(Integer lastSalePriceFlgUpDown) {
        this.lastSalePriceFlgUpDown = lastSalePriceFlgUpDown;
    }

    public Integer getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(Integer totalSold) {
        this.totalSold = totalSold;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getViewURL() {
        return viewURL;
    }

    public void setViewURL(String viewURL) {
        this.viewURL = viewURL;
    }


}
