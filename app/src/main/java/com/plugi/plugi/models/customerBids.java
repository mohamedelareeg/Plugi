package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plugi.plugi.models.itemDetails.ExpirationDaysList;
import com.plugi.plugi.models.orderDetails.CurrentList;
import com.plugi.plugi.models.orderDetails.HistoryList;
import com.plugi.plugi.models.orderDetails.PendingList;

import java.io.Serializable;
import java.util.List;

public class customerBids implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CustomerID")
    @Expose
    private Integer customerID;
    @SerializedName("CurrentList")
    @Expose
    private List<CurrentList> currentList = null;
    @SerializedName("PendingList")
    @Expose
    private List<PendingList> pendingList = null;
    @SerializedName("HistoryList")
    @Expose
    private List<HistoryList> historyList = null;
    @SerializedName("ExpirationDaysList")
    @Expose
    private List<ExpirationDaysList> expirationDaysList = null;
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

    public List<CurrentList> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(List<CurrentList> currentList) {
        this.currentList = currentList;
    }

    public List<PendingList> getPendingList() {
        return pendingList;
    }

    public void setPendingList(List<PendingList> pendingList) {
        this.pendingList = pendingList;
    }

    public List<HistoryList> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<HistoryList> historyList) {
        this.historyList = historyList;
    }

    public List<ExpirationDaysList> getExpirationDaysList() {
        return expirationDaysList;
    }

    public void setExpirationDaysList(List<ExpirationDaysList> expirationDaysList) {
        this.expirationDaysList = expirationDaysList;
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
