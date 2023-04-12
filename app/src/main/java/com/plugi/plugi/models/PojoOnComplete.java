package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PojoOnComplete {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("BrandId")
    @Expose
    private Integer brandId;
    @SerializedName("StatusID")
    @Expose
    private Integer statusID;
    @SerializedName("SearchKey")
    @Expose
    private String searchKey;
    @SerializedName("ResultCount")
    @Expose
    private Integer resultCount;

    public PojoOnComplete(String $id, Integer categoryID, Integer brandId, Integer statusID, String searchKey, Integer resultCount) {
        this.$id = $id;
        this.categoryID = categoryID;
        this.brandId = brandId;
        this.statusID = statusID;
        this.searchKey = searchKey;
        this.resultCount = resultCount;
    }

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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }
}
