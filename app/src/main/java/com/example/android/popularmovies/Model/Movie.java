    package com.example.android.popularmovies.Model;

    import android.arch.persistence.room.Entity;
    import android.arch.persistence.room.PrimaryKey;
    import android.os.Parcel;
    import android.os.Parcelable;
    import android.support.annotation.NonNull;

    import com.google.gson.annotations.SerializedName;

    @Entity(tableName = "movie")
    public class Movie implements Parcelable {

        @NonNull
        @PrimaryKey
        @SerializedName("id")
        private String id;
        @SerializedName("title")
        private String title;
        @SerializedName("poster_path")
        private String poster_path;
        @SerializedName("vote_average")
        private String rating;
        @SerializedName("overview")
        private String overview;
        @SerializedName("release_date")
        private String dateRelease;

        // parameter constructor
        public Movie (@NonNull String id, String title, String poster_path, String overview, String rating, String dateRelease){
            this.id = id;
            this.title = title;
            this.poster_path = poster_path;
            this.rating = rating;
            this.overview = overview;
            this.dateRelease = dateRelease;
        }


        //Using Parcel constructor
        protected Movie(Parcel source) {
            this.id = source.readString();
            this.title = source.readString();
            this.poster_path = source.readString();
            this.rating = source.readString();
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
        public String getId()           {   return id; }
        public String getTitle()        {   return title;       }
        public String getPoster_path()  { return poster_path;   }
        public String getRating()       {   return rating;      }
        public String getOverview()     {   return overview;    }
        public String getDateRelease()  {   return dateRelease; }

        //set modifier
        public void setId(@NonNull String id)           { this.id = id; }
        public void setTitle(String title)              { this.title = title; }
        public void setPoster_path(String poster_path) { this.poster_path = poster_path; }
        public void setRating(String rating)            { this.rating = rating; }
        public void setOverview(String overview)        { this.overview = overview; }
        public void setDateRelease(String dateRelease)  { this.dateRelease = dateRelease; }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.title);
            dest.writeString(this.poster_path);
            dest.writeString(this.rating);
            dest.writeString(this.overview);
            dest.writeString(this.dateRelease);
        }
    }
