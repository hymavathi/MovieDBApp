package com.udacity.projects.moviesdbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Review implements Parcelable
{

        private String id;

        private String content;

        private String author;

        private String url;


    public Review(String author , String content) {
        this.content = content;
        this.author = author;
    }

    protected Review(Parcel in) {
        id = in.readString();
        content = in.readString();
        author = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(author);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getContent ()
        {
            return content;
        }

        public void setContent (String content)
        {
            this.content = content;
        }

        public String getAuthor ()
        {
            return author;
        }

        public void setAuthor (String author)
        {
            this.author = author;
        }

        public String getUrl ()
        {
            return url;
        }

        public void setUrl (String url)
        {
            this.url = url;
        }


}
