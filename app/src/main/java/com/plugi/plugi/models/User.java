package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Device_ID")
    @Expose
    private String deviceID;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("Customer")
    @Expose
    private Customer customer;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
    public class ShozeSizeList implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Name")
        @Expose
        private String name;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class ClothSizeList implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Name")
        @Expose
        private String name;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class Customer implements Serializable {

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
        @SerializedName("EmailAddress")
        @Expose
        private String emailAddress;
        @SerializedName("MobileCode")
        @Expose
        private String mobileCode;
        @SerializedName("MobileNumber")
        @Expose
        private String mobileNumber;
        @SerializedName("ShoesSizeID")
        @Expose
        private Integer shoesSizeID;
        @SerializedName("ShoesSizeValue")
        @Expose
        private String shoesSizeValue;
        @SerializedName("ClothSizeID")
        @Expose
        private Integer clothSizeID;
        @SerializedName("ClothSizeValue")
        @Expose
        private String clothSizeValue;

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

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getMobileCode() {
            return mobileCode;
        }

        public void setMobileCode(String mobileCode) {
            this.mobileCode = mobileCode;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public Integer getShoesSizeID() {
            return shoesSizeID;
        }

        public void setShoesSizeID(Integer shoesSizeID) {
            this.shoesSizeID = shoesSizeID;
        }

        public String getShoesSizeValue() {
            return shoesSizeValue;
        }

        public void setShoesSizeValue(String shoesSizeValue) {
            this.shoesSizeValue = shoesSizeValue;
        }

        public Integer getClothSizeID() {
            return clothSizeID;
        }

        public void setClothSizeID(Integer clothSizeID) {
            this.clothSizeID = clothSizeID;
        }

        public String getClothSizeValue() {
            return clothSizeValue;
        }

        public void setClothSizeValue(String clothSizeValue) {
            this.clothSizeValue = clothSizeValue;
        }

    }
}
