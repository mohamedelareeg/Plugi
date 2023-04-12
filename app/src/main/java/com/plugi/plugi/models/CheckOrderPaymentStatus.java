package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOrderPaymentStatus {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CustomerID")
    @Expose
    private Integer customerID;
    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("ItemID")
    @Expose
    private Integer itemID;
    @SerializedName("IsPaidStatus")
    @Expose
    private Boolean isPaidStatus;
    @SerializedName("TransactionId")
    @Expose
    private String transactionId;
    @SerializedName("TransactionStatus")
    @Expose
    private String transactionStatus;
    @SerializedName("TransationValue")
    @Expose
    private String transationValue;
    @SerializedName("TransationDate")
    @Expose
    private String transationDate;
    @SerializedName("ReferenceId")
    @Expose
    private String referenceId;
    @SerializedName("AuthorizationId")
    @Expose
    private String authorizationId;
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

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public Boolean getIsPaidStatus() {
        return isPaidStatus;
    }

    public void setIsPaidStatus(Boolean isPaidStatus) {
        this.isPaidStatus = isPaidStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransationValue() {
        return transationValue;
    }

    public void setTransationValue(String transationValue) {
        this.transationValue = transationValue;
    }

    public String getTransationDate() {
        return transationDate;
    }

    public void setTransationDate(String transationDate) {
        this.transationDate = transationDate;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
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
