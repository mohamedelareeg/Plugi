package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetCustomerWallet implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CustomerID")
    @Expose
    private Integer customerID;
    @SerializedName("TotalBalance")
    @Expose
    private Double totalBalance;
    @SerializedName("CurrencePrice")
    @Expose
    private String currencePrice;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;
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

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getCurrencePrice() {
        return currencePrice;
    }

    public void setCurrencePrice(String currencePrice) {
        this.currencePrice = currencePrice;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
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
    public class Detail implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("OrderNo")
        @Expose
        private Integer orderNo;
        @SerializedName("OrderDate")
        @Expose
        private String orderDate;
        @SerializedName("OrderTotalCost")
        @Expose
        private Double orderTotalCost;
        @SerializedName("TransactionDate")
        @Expose
        private String transactionDate;
        @SerializedName("TransactionID")
        @Expose
        private Integer transactionID;
        @SerializedName("TransactionType")
        @Expose
        private String transactionType;
        @SerializedName("TranactionAmount")
        @Expose
        private String tranactionAmount;
        @SerializedName("PriceCurrency")
        @Expose
        private String priceCurrency;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public Integer getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(Integer orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public Double getOrderTotalCost() {
            return orderTotalCost;
        }

        public void setOrderTotalCost(Double orderTotalCost) {
            this.orderTotalCost = orderTotalCost;
        }

        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
        }

        public Integer getTransactionID() {
            return transactionID;
        }

        public void setTransactionID(Integer transactionID) {
            this.transactionID = transactionID;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getTranactionAmount() {
            return tranactionAmount;
        }

        public void setTranactionAmount(String tranactionAmount) {
            this.tranactionAmount = tranactionAmount;
        }

        public String getPriceCurrency() {
            return priceCurrency;
        }

        public void setPriceCurrency(String priceCurrency) {
            this.priceCurrency = priceCurrency;
        }

    }

}
