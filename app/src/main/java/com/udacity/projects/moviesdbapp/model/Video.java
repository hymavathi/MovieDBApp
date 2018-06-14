package com.udacity.projects.moviesdbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Video implements Parcelable
{
    private String site;

    private String id;

    private String iso_639_1;

    private String name;

    private String type;

    private String key;

    private String iso_3166_1;

    private String size;


    protected Video(Parcel in) {
        site = in.readString();
        id = in.readString();
        iso_639_1 = in.readString();
        name = in.readString();
        type = in.readString();
        key = in.readString();
        iso_3166_1 = in.readString();
        size = in.readString();
    }

    public Video(String name , String key , String type ) {
        this.name = name;
        this.type = type;
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(key);
        dest.writeString(type);
        dest.writeString(site);
        dest.writeString(id);
        dest.writeString(iso_639_1);
        dest.writeString(iso_3166_1);
        dest.writeString(size);
    }




       public String getSite ()
       {
           return site;
       }

       public void setSite (String site)
       {
           this.site = site;
       }

       public String getId ()
       {
           return id;
       }

       public void setId (String id)
       {
           this.id = id;
       }

       public String getIso_639_1 ()
       {
           return iso_639_1;
       }

       public void setIso_639_1 (String iso_639_1)
       {
           this.iso_639_1 = iso_639_1;
       }

       public String getName ()
       {
           return name;
       }

       public void setName (String name)
       {
           this.name = name;
       }

       public String getType ()
       {
           return type;
       }

       public void setType (String type)
       {
           this.type = type;
       }

       public String getKey ()
       {
           return key;
       }

       public void setKey (String key)
       {
           this.key = key;
       }

       public String getIso_3166_1 ()
       {
           return iso_3166_1;
       }

       public void setIso_3166_1 (String iso_3166_1)
       {
           this.iso_3166_1 = iso_3166_1;
       }

       public String getSize ()
       {
           return size;
       }

       public void setSize (String size)
       {
           this.size = size;
       }

}