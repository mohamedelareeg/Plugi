package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plugi.plugi.models.itemDetails.ItemDetail;

import java.io.Serializable;

public class ViewBidDetails implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CustomerID")
    @Expose
    private Integer customerID;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Item_detail")
    @Expose
    private ItemDetail itemDetail;
    @SerializedName("UnitSizeType")
    @Expose
    private String unitSizeType;
    @SerializedName("GenderSizeType")
    @Expose
    private String genderSizeType;
    @SerializedName("ColorName")
    @Expose
    private String colorName;
    @SerializedName("CondationName")
    @Expose
    private String condationName;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("PriceCurrency")
    @Expose
    private String priceCurrency;
    @SerializedName("ProcessingFees")
    @Expose
    private Double processingFees;
    @SerializedName("ShippingCost")
    @Expose
    private Double shippingCost;
    @SerializedName("TaxFees")
    @Expose
    private Integer taxFees;
    @SerializedName("TotalCost")
    @Expose
    private Double totalCost;
    @SerializedName("HighestBid")
    @Expose
    private Double highestBid;
    @SerializedName("LowestASK")
    @Expose
    private Double lowestASK;
    @SerializedName("Release_Date")
    @Expose
    private String releaseDate;
    @SerializedName("ExpireDate")
    @Expose
    private String expireDate;
    @SerializedName("Discount_Cost")
    @Expose
    private Double discountCost;
    @SerializedName("Plugi_Fees")
    @Expose
    private Double plugiFees;
    @SerializedName("Language_ID")
    @Expose
    private Integer languageID;
    @SerializedName("Currency_ID")
    @Expose
    private Integer currencyID;
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public ItemDetail getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }

    public String getUnitSizeType() {
        return unitSizeType;
    }

    public void setUnitSizeType(String unitSizeType) {
        this.unitSizeType = unitSizeType;
    }

    public String getGenderSizeType() {
        return genderSizeType;
    }

    public void setGenderSizeType(String genderSizeType) {
        this.genderSizeType = genderSizeType;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getCondationName() {
        return condationName;
    }

    public void setCondationName(String condationName) {
        this.condationName = condationName;
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

    public Double getProcessingFees() {
        return processingFees;
    }

    public void setProcessingFees(Double processingFees) {
        this.processingFees = processingFees;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Integer getTaxFees() {
        return taxFees;
    }

    public void setTaxFees(Integer taxFees) {
        this.taxFees = taxFees;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Double highestBid) {
        this.highestBid = highestBid;
    }

    public Double getLowestASK() {
        return lowestASK;
    }

    public void setLowestASK(Double lowestASK) {
        this.lowestASK = lowestASK;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Double getDiscountCost() {
        return discountCost;
    }

    public void setDiscountCost(Double discountCost) {
        this.discountCost = discountCost;
    }

    public Double getPlugiFees() {
        return plugiFees;
    }

    public void setPlugiFees(Double plugiFees) {
        this.plugiFees = plugiFees;
    }

    public Integer getLanguageID() {
        return languageID;
    }

    public void setLanguageID(Integer languageID) {
        this.languageID = languageID;
    }

    public Integer getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(Integer currencyID) {
        this.currencyID = currencyID;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
