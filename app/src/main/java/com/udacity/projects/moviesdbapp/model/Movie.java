package com.udacity.projects.moviesdbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {


        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public Movie(String title, String poster_path, int id, String release_date) {
        this.title = title;
        this.posterPath = poster_path;
        this.releaseDate = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(Parcel parcel) {
        voteCount = parcel.readLong();
        id = parcel.readLong();
        voteAverage = parcel.readFloat();
        title = parcel.readString();
        posterPath = parcel.readString();
        backdropPath = parcel.readString();
        overview = parcel.readString();
        releaseDate = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(voteCount);
        dest.writeLong(id);
        dest.writeFloat(voteAverage);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeInt(isFavorite ? 1: 0);
    }

    public Movie() {
    }

    @SerializedName("vote_count")
    private Long voteCount;

    @SerializedName("id")
    private Long id;

    @SerializedName("video")
    private Boolean video;

    @SerializedName("vote_average")
    private Float voteAverage;

    @SerializedName("title")
    private String title;

    @SerializedName("popularity")
    private Float popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("genre_ids")
    private List<Long> genreIds;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("adult")
    private Boolean adult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    private boolean isFavorite;
    private List<Video> videos;
    private List<Review> reviews;

    //getters and setters

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
