package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerAddress implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
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
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("IsBillingSameShippingAddress")
    @Expose
    private Boolean isBillingSameShippingAddress;
    @SerializedName("Bill_FirstName")
    @Expose
    private String billFirstName;
    @SerializedName("Bill_LastName")
    @Expose
    private String billLastName;
    @SerializedName("Bill_Country_ID")
    @Expose
    private Integer billCountryID;
    @SerializedName("Bill_Country_Name")
    @Expose
    private String billCountryName;
    @SerializedName("Bill_Address_1")
    @Expose
    private String billAddress1;
    @SerializedName("Bill_Address_2")
    @Expose
    private String billAddress2;
    @SerializedName("Bill_CityId")
    @Expose
    private Integer billCityId;
    @SerializedName("Bill_CityName")
    @Expose
    private String billCityName;
    @SerializedName("Bill_ZipCode")
    @Expose
    private String billZipCode;
    @SerializedName("Bill_PhoneNumber")
    @Expose
    private String billPhoneNumber;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Boolean getIsBillingSameShippingAddress() {
        return isBillingSameShippingAddress;
    }

    public void setIsBillingSameShippingAddress(Boolean isBillingSameShippingAddress) {
        this.isBillingSameShippingAddress = isBillingSameShippingAddress;
    }

    public String getBillFirstName() {
        return billFirstName;
    }

    public void setBillFirstName(String billFirstName) {
        this.billFirstName = billFirstName;
    }

    public String getBillLastName() {
        return billLastName;
    }

    public void setBillLastName(String billLastName) {
        this.billLastName = billLastName;
    }

    public Integer getBillCountryID() {
        return billCountryID;
    }

    public void setBillCountryID(Integer billCountryID) {
        this.billCountryID = billCountryID;
    }

    public String getBillCountryName() {
        return billCountryName;
    }

    public void setBillCountryName(String billCountryName) {
        this.billCountryName = billCountryName;
    }

    public String getBillAddress1() {
        return billAddress1;
    }

    public void setBillAddress1(String billAddress1) {
        this.billAddress1 = billAddress1;
    }

    public String getBillAddress2() {
        return billAddress2;
    }

    public void setBillAddress2(String billAddress2) {
        this.billAddress2 = billAddress2;
    }

    public Integer getBillCityId() {
        return billCityId;
    }

    public void setBillCityId(Integer billCityId) {
        this.billCityId = billCityId;
    }

    public String getBillCityName() {
        return billCityName;
    }

    public void setBillCityName(String billCityName) {
        this.billCityName = billCityName;
    }

    public String getBillZipCode() {
        return billZipCode;
    }

    public void setBillZipCode(String billZipCode) {
        this.billZipCode = billZipCode;
    }

    public String getBillPhoneNumber() {
        return billPhoneNumber;
    }

    public void setBillPhoneNumber(String billPhoneNumber) {
        this.billPhoneNumber = billPhoneNumber;
    }

}