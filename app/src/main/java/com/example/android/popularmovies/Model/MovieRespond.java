package com.example.android.popularmovies.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class MovieRespond {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("results")
    public List<Movie> movieslist;

    //Access modifiero
    public int getPage()                { return page; }
    public int getTotal_results()       { return total_results; }
    public int getTotal_pages()         { return total_pages; }
    public List<Movie> getMovieslist()  { return movieslist; }

    //Set modifier
    public void setPage(int page)                       { this.page = page; }
    public void setTotal_results(int total_results)     { this.total_results = total_results; }
    public void setTotal_pages(int total_pages)         { this.total_pages = total_pages; }
    public void setMovieslist(List<Movie> movieslist)   { this.movieslist = movieslist; }
}
