    package com.example.android.popularmovies.Model;


    import android.arch.persistence.room.Entity;
    import android.arch.persistence.room.PrimaryKey;
    import android.os.Parcel;
    import android.os.Parcelable;
    import android.support.annotation.NonNull;

    import com.google.gson.annotations.SerializedName;

    @Entity(tableName = "movieTask")
    public class Movie implements Parcelable {

        @NonNull
        @PrimaryKey
        @SerializedName("id")
        private int id;
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

        // parameter constructor
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

        //Using Parcel constructor
        protected Movie(Parcel source) {
            this.id = source.readInt();
            this.title = source.readString();
            this.poster = source.readString();
            this.rating = source.readString();
            this.popularity = source.readDouble();
            this.overview = source.readString();
            this.dateRelease = source.readString();
        }

        // interface Parcelable callback to parcel
        public static final Parcelable.Creator CREATOR = new  Parcelable.Creator(){
            @Override
            public Movie createFromParcel(Parcel source) {
                return new Movie(source);
            }
            @Override
            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };

        // access modifier
        @NonNull
        public int getId()              {   return id; }
        public String getTitle()        {   return title;       }
        public String getPosterUrl()    {   return poster;      }
        public String getRating()       {   return rating;      }
        public Double getPopularity()   {   return popularity;  }
        public String getOverview()     {   return overview;    }
        public String getDateRelease()  {   return dateRelease; }

        //set modifier
        public void setId()                             { this.id = id; }
        public void setTitle(String title)              { this.title = title; }
        public void setPosterUrl(String poster)         { this.poster = poster; }
        public void setRating(String rating)            { this.rating = rating; }
        public void setPopularity(Double Popularity)    { this.popularity = popularity; }
        public void setOverview(String overview)        { this.overview = overview; }
        public void setDateRelease(String dateRelease)  { this.dateRelease = dateRelease; }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.title);
            dest.writeString(this.poster);
            dest.writeString(this.rating);
            dest.writeDouble(this.popularity);
            dest.writeString(this.overview);
            dest.writeString(this.dateRelease);
        }
    }
