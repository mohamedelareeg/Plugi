package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchHistory implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("SearchKey")
    @Expose
    private String searchKey;
    @SerializedName("AutoComplateItemList")
    @Expose
    private String autoComplateItemList;
    @SerializedName("RecentSearchWords")
    @Expose
    private List<RecentSearchWord> recentSearchWords = null;
    @SerializedName("TrendingSearchWords")
    @Expose
    private List<TrendingSearchWord> trendingSearchWords = null;
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

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getAutoComplateItemList() {
        return autoComplateItemList;
    }

    public void setAutoComplateItemList(String autoComplateItemList) {
        this.autoComplateItemList = autoComplateItemList;
    }

    public List<RecentSearchWord> getRecentSearchWords() {
        return recentSearchWords;
    }

    public void setRecentSearchWords(List<RecentSearchWord> recentSearchWords) {
        this.recentSearchWords = recentSearchWords;
    }

    public List<TrendingSearchWord> getTrendingSearchWords() {
        return trendingSearchWords;
    }

    public void setTrendingSearchWords(List<TrendingSearchWord> trendingSearchWords) {
        this.trendingSearchWords = trendingSearchWords;
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
    public class RecentSearchWord implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("Name")
        @Expose
        private String name;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class TrendingSearchWord implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("Name")
        @Expose
        private String name;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
