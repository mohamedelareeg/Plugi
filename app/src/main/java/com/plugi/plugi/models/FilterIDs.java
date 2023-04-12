package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FilterIDs implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("BrandsList")
    @Expose
    private List<BrandsList> brandsList = null;
    @SerializedName("SizeTypeList")
    @Expose
    private List<SizeTypeList> sizeTypeList = null;
    @SerializedName("SizeList")
    @Expose
    private List<SizeList> sizeList = null;
    @SerializedName("PriceList")
    @Expose
    private List<PriceList> priceList = null;
    @SerializedName("ReleaseYearList")
    @Expose
    private List<ReleaseYearList> releaseYearList = null;
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

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public List<BrandsList> getBrandsList() {
        return brandsList;
    }

    public void setBrandsList(List<BrandsList> brandsList) {
        this.brandsList = brandsList;
    }

    public List<SizeTypeList> getSizeTypeList() {
        return sizeTypeList;
    }

    public void setSizeTypeList(List<SizeTypeList> sizeTypeList) {
        this.sizeTypeList = sizeTypeList;
    }

    public List<SizeList> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<SizeList> sizeList) {
        this.sizeList = sizeList;
    }

    public List<PriceList> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<PriceList> priceList) {
        this.priceList = priceList;
    }

    public List<ReleaseYearList> getReleaseYearList() {
        return releaseYearList;
    }

    public void setReleaseYearList(List<ReleaseYearList> releaseYearList) {
        this.releaseYearList = releaseYearList;
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
    public class BrandsList implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("Category_ID")
        @Expose
        private Integer categoryID;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Parent_ID")
        @Expose
        private Integer parentID;
        @SerializedName("ImageURL")
        @Expose
        private String imageURL;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public Integer getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(Integer categoryID) {
            this.categoryID = categoryID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getParentID() {
            return parentID;
        }

        public void setParentID(Integer parentID) {
            this.parentID = parentID;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

    }


    public class PriceList implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Desc")
        @Expose
        private String desc;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    }
    public class ReleaseYearList implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Desc")
        @Expose
        private String desc;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    }
    public class SizeList implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Desc")
        @Expose
        private String desc;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    }
    public class SizeTypeList implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Desc")
        @Expose
        private String desc;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
