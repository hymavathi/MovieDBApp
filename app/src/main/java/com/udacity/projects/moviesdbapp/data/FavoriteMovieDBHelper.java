package com.udacity.projects.moviesdbapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteMovieDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movie.db";

    public FavoriteMovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_FAVORITE_MOVIE_TABLE_SQL = "CREATE TABLE " + FavoriteMovieContract.FavMovieEntry.TABLE_NAME + "( "
                + FavoriteMovieContract.FavMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL , "
                + FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL , "
                + FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_BACK_DROP_PATH + " TEXT NOT NULL , "
                + FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL , "
                + FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL , "
                + FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL , "
                + FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_RATE + " INTEGER NOT NULL "
                + ");";

        final String CREATE_VIDEO_TABLE_SQL = "CREATE TABLE " + FavoriteMovieContract.VideoEntry.TABLE_NAME + "( "
                + FavoriteMovieContract.VideoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FavoriteMovieContract.VideoEntry.COLUMN_MOVIE_ID  + " INTEGER NOT NULL , "
                + FavoriteMovieContract.VideoEntry.COLUMN_VIDEO_NAME +" TEXT NOT NULL , "
                + FavoriteMovieContract.VideoEntry.COLUMN_VIDEO_KEY + "TEXT NOT NULL , "
                + FavoriteMovieContract.VideoEntry.COLUMN_VIDEO_TYPE + " TEXT NOT NULL, "
                + " FOREIGN KEY ("+ FavoriteMovieContract.VideoEntry.COLUMN_MOVIE_ID +") REFERENCES " + FavoriteMovieContract.FavMovieEntry.TABLE_NAME + "(" + FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_ID + ")"
                + ");";

        final String CREATE_REVIEW_TABLE_SQL = "CREATE TABLE " + FavoriteMovieContract.ReviewEntry.TABLE_NAME + "( "
                + FavoriteMovieContract.ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FavoriteMovieContract.ReviewEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "
                + FavoriteMovieContract.ReviewEntry.COLUMN_REVIEW_AUTHOR + " TEXT NOT NULL, "
                + FavoriteMovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT + " TEXT NOT NULL, "
                + " FOREIGN KEY ("+ FavoriteMovieContract.ReviewEntry.COLUMN_MOVIE_ID +") REFERENCES " + FavoriteMovieContract.FavMovieEntry.TABLE_NAME + "(" + FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_ID + ")"
                + ");";

        db.execSQL(CREATE_FAVORITE_MOVIE_TABLE_SQL);
        db.execSQL(CREATE_VIDEO_TABLE_SQL);
        db.execSQL(CREATE_REVIEW_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieContract.FavMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieContract.VideoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieContract.ReviewEntry.TABLE_NAME);

        onCreate(db);
    }
}
