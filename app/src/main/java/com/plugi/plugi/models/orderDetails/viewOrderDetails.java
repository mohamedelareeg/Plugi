package com.plugi.plugi.models.orderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plugi.plugi.models.itemDetails.ItemDetail;

public class viewOrderDetails {
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
    @SerializedName("PurchasePrice")
    @Expose
    private Double purchasePrice;
    @SerializedName("PriceCurrency")
    @Expose
    private String priceCurrency;
    @SerializedName("Transaction_Fees")
    @Expose
    private Double transactionFees;
    @SerializedName("Payment_Cost")
    @Expose
    private Double paymentCost;
    @SerializedName("ShippingCost")
    @Expose
    private Double shippingCost;
    @SerializedName("TaxFees")
    @Expose
    private Integer taxFees;
    @SerializedName("Discount_Cost")
    @Expose
    private Double discountCost;
    @SerializedName("Plugi_Fees")
    @Expose
    private Double plugiFees;
    @SerializedName("TotalCost")
    @Expose
    private Double totalCost;
    @SerializedName("HighestBid")
    @Expose
    private Double highestBid;
    @SerializedName("LowestASK")
    @Expose
    private Double lowestASK;
    @SerializedName("Order_Date")
    @Expose
    private String orderDate;
    @SerializedName("OrderNo")
    @Expose
    private Integer orderNo;
    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("Shipping_Address_desc")
    @Expose
    private String shippingAddressDesc;
    @SerializedName("PaymentCard_desc")
    @Expose
    private String paymentCardDesc;
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

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public Double getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(Double transactionFees) {
        this.transactionFees = transactionFees;
    }

    public Double getPaymentCost() {
        return paymentCost;
    }

    public void setPaymentCost(Double paymentCost) {
        this.paymentCost = paymentCost;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingAddressDesc() {
        return shippingAddressDesc;
    }

    public void setShippingAddressDesc(String shippingAddressDesc) {
        this.shippingAddressDesc = shippingAddressDesc;
    }

    public String getPaymentCardDesc() {
        return paymentCardDesc;
    }

    public void setPaymentCardDesc(String paymentCardDesc) {
        this.paymentCardDesc = paymentCardDesc;
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
