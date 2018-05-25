    package com.example.android.popularmovies.Model;


    import com.google.gson.annotations.SerializedName;

    public class Movie {

        @SerializedName("title")
        private String title;
        @SerializedName("poster_path")
        private String poster;
        @SerializedName("vote_average")
        private String rating;
        @SerializedName("overview")
        private String overview;
        @SerializedName("release_date")
        private String dateRelease;
        @SerializedName("popularity")
        private Double popularity;
        @SerializedName("id")
        private int id;

        public Movie(String mTitle, int id, String poster, String rating, String dateRelease, String title){
            this.title = title;
        }
        // parameter constructer
        public Movie (String title, String poster, String overview, String popularVote, String rating,
                      String dateRelease, int id, double popularity){
            this.id = id;
            this.title = title;
            this.poster = poster;
            this.rating = rating;
            this.popularity = popularity;
            this.overview = overview;
            this.dateRelease = dateRelease;
        }

        // access modifier

        public int getId()              {   return id; }
        public String getTitle()        {   return title;       }
        public String getPoster()       {   return poster;      }
        public String getRating()       {   return rating;      }
        public Double getPopularity()   {   return popularity;  }
        public String getOverview()     {   return overview;    }
        public String getDateRelease()  {   return dateRelease; }

        //set modifier
        public void setId()                             { this.id = id; }
        public void setTitle(String title)              { this.title = title; }
        public void setPoster(String poster)            { this.poster = poster; }
        public void setRating(String rating)            { this.rating = rating; }
        public void setPopularity(Double Popularity)    { this.popularity = popularity; }
        public void setOverview(String overview)        { this.overview = overview; }
        public void setDateRelease(String dateRelease)  { this.dateRelease = dateRelease; }
    }
