package com.example.android.popularmovies.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "movies_info")
public class MoviesEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mtitle;
    private Double mrating;

    public MoviesEntry (int id, String title, double rating){
        this.id = id;
        mtitle = title;
        mrating = rating;
    }


}
