package com.plugi.plugi.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerShippingAddress implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Customer_ID")
    @Expose
    private Integer customerID;
    @SerializedName("Country_ID")
    @Expose
    private Integer countryID;
    @SerializedName("Country_Name")
    @Expose
    private String countryName;
    @SerializedName("Address_1")
    @Expose
    private String address1;
    @SerializedName("Address_2")
    @Expose
    private String address2;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("RegionName")
    @Expose
    private String regionName;
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
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
    private Object statusMessage;

    public CustomerShippingAddress(String $id, Integer iD, Integer customerID, Integer countryID, String countryName, String address1, String address2, Integer cityId, String cityName, String regionName, String zipCode, String phoneNumber, Integer languageID, Integer currencyID) {
        this.$id = $id;
        this.iD = iD;
        this.customerID = customerID;
        this.countryID = countryID;
        this.countryName = countryName;
        this.address1 = address1;
        this.address2 = address2;
        this.cityId = cityId;
        this.cityName = cityName;
        this.regionName = regionName;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.languageID = languageID;
        this.currencyID = currencyID;
    }

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

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Object getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(Object statusMessage) {
        this.statusMessage = statusMessage;
    }

}
