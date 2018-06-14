package com.udacity.projects.moviesdbapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewRestResponse {


    @SerializedName("id")
    private long id;

    @SerializedName("page")
    private String page;

    @SerializedName("results")
    private List<Review> results;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
