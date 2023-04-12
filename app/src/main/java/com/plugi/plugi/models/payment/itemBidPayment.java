package com.plugi.plugi.models.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class itemBidPayment  implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Item_ID")
    @Expose
    private Integer itemID;
    @SerializedName("Size_ID")
    @Expose
    private Integer sizeID;
    @SerializedName("Units")
    @Expose
    private Integer units;
    @SerializedName("Customer_Id")
    @Expose
    private Integer customerId;
    @SerializedName("Release_Date")
    @Expose
    private String releaseDate;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Expiry_Date")
    @Expose
    private String expiryDate;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("Discount_ID")
    @Expose
    private Integer discountID;
    @SerializedName("Discount_Cost")
    @Expose
    private Double discountCost;
    @SerializedName("Bid_Cost")
    @Expose
    private Double bidCost;
    @SerializedName("Shipping_Cost")
    @Expose
    private Double shippingCost;
    @SerializedName("Plugi_Cost")
    @Expose
    private Double plugiCost;
    @SerializedName("Total_Cost")
    @Expose
    private Double totalCost;
    @SerializedName("CustomerAddress_ID")
    @Expose
    private Integer customerAddressID;
    @SerializedName("CustomerCard_ID")
    @Expose
    private Integer customerCardID;
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

    public Integer getSizeID() {
        return sizeID;
    }

    public void setSizeID(Integer sizeID) {
        this.sizeID = sizeID;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getDiscountID() {
        return discountID;
    }

    public void setDiscountID(Integer discountID) {
        this.discountID = discountID;
    }

    public Double getDiscountCost() {
        return discountCost;
    }

    public void setDiscountCost(Double discountCost) {
        this.discountCost = discountCost;
    }

    public Double getBidCost() {
        return bidCost;
    }

    public void setBidCost(Double bidCost) {
        this.bidCost = bidCost;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Double getPlugiCost() {
        return plugiCost;
    }

    public void setPlugiCost(Double plugiCost) {
        this.plugiCost = plugiCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getCustomerAddressID() {
        return customerAddressID;
    }

    public void setCustomerAddressID(Integer customerAddressID) {
        this.customerAddressID = customerAddressID;
    }

    public Integer getCustomerCardID() {
        return customerCardID;
    }

    public void setCustomerCardID(Integer customerCardID) {
        this.customerCardID = customerCardID;
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
