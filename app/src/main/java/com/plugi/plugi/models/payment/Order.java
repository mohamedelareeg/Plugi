package com.plugi.plugi.models.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {

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
    @SerializedName("Customer_ID")
    @Expose
    private Integer customerID;
    @SerializedName("Bider_ID")
    @Expose
    private Integer biderID;
    @SerializedName("Bider_Cost")
    @Expose
    private Double biderCost;
    @SerializedName("Plugi_Fees")
    @Expose
    private Double plugiFees;
    @SerializedName("Units")
    @Expose
    private Integer units;
    @SerializedName("Transaction_Fees")
    @Expose
    private Double transactionFees;
    @SerializedName("Payment_Cost")
    @Expose
    private Double paymentCost;
    @SerializedName("Shipping_Cost")
    @Expose
    private Double shippingCost;
    @SerializedName("Discount_ID")
    @Expose
    private Integer discountID;
    @SerializedName("Discount_Cost")
    @Expose
    private Double discountCost;
    @SerializedName("Total_Payment")
    @Expose
    private Double totalPayment;
    @SerializedName("Saller_ID")
    @Expose
    private Integer sallerID;
    @SerializedName("Saller_Cost")
    @Expose
    private Double sallerCost;
    @SerializedName("Shipping_Address_ID")
    @Expose
    private Integer shippingAddressID;
    @SerializedName("PaymentCard_ID")
    @Expose
    private Integer paymentCardID;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Transaction_Type")
    @Expose
    private Integer transactionType;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("OrderDetails")
    @Expose
    private Object orderDetails;
    @SerializedName("OrderNotes")
    @Expose
    private Object orderNotes;
    @SerializedName("AdminNotes")
    @Expose
    private Object adminNotes;
    @SerializedName("IsConfirmed")
    @Expose
    private Boolean isConfirmed;

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

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getBiderID() {
        return biderID;
    }

    public void setBiderID(Integer biderID) {
        this.biderID = biderID;
    }

    public Double getBiderCost() {
        return biderCost;
    }

    public void setBiderCost(Double biderCost) {
        this.biderCost = biderCost;
    }

    public Double getPlugiFees() {
        return plugiFees;
    }

    public void setPlugiFees(Double plugiFees) {
        this.plugiFees = plugiFees;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
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

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Integer getSallerID() {
        return sallerID;
    }

    public void setSallerID(Integer sallerID) {
        this.sallerID = sallerID;
    }

    public Double getSallerCost() {
        return sallerCost;
    }

    public void setSallerCost(Double sallerCost) {
        this.sallerCost = sallerCost;
    }

    public Integer getShippingAddressID() {
        return shippingAddressID;
    }

    public void setShippingAddressID(Integer shippingAddressID) {
        this.shippingAddressID = shippingAddressID;
    }

    public Integer getPaymentCardID() {
        return paymentCardID;
    }

    public void setPaymentCardID(Integer paymentCardID) {
        this.paymentCardID = paymentCardID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Object getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Object orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Object getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(Object orderNotes) {
        this.orderNotes = orderNotes;
    }

    public Object getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(Object adminNotes) {
        this.adminNotes = adminNotes;
    }

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

}
