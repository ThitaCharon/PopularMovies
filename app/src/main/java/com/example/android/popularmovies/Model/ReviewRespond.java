package com.example.android.popularmovies.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewRespond {

    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    List<Review> reviews;
    @SerializedName("total_page")
    private int total_page;
    @SerializedName("total_results")
    private int total_results;

    // constructor
    public ReviewRespond(int id, int page, List<Review> reviews, int total_page, int total_results) {
        this.id = id;
        this.page = page;
        this.reviews = reviews;
        this.total_page = total_page;
        this.total_results = total_results;
    }

    //getter
    public int getId() { return id; }
    public int getPage() { return page; }
    public List<Review> getReviewsList() { return reviews; }
    public int getTotal_page() { return total_page; }
    public int getTotal_results() { return total_results; }

    //setter
    public void setId(int id) { this.id = id; }
    public void setPage(int page) { this.page = page; }
    public void setReviewsList(List<Review> reviews) { this.reviews = reviews; }
    public void setTotal_page(int total_page) { this.total_page = total_page; }
    public void setTotal_results(int total_results) { this.total_results = total_results; }
}
