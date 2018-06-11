package com.example.android.popularmovies.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoRespond {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Video> videosList;

    public int getId () {return id; }
    public List<Video> getVideoslist()  { return videosList; }

    // setter
    public void setId (int id){ this.id = id;}
    public void setVideoslist(List<Video> vList)  { this.videosList =  vList; }
}
