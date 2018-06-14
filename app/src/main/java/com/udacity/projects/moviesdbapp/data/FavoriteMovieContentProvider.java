package com.udacity.projects.moviesdbapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavoriteMovieContentProvider extends ContentProvider {
    private static final String TAG = FavoriteMovieContentProvider.class.getSimpleName();
    private FavoriteMovieDBHelper favoriteMoviesDBHelper;

    // authority to avoid naming conflicts
    private static final String AUTHORITY = "com.moviesdbapp.contentprovider";

    private static final int MOVIES_CODE = 100;
    private static final int MOVIES_WITH_ID_CODE = 101;
    private static final int VIDEOS_CODE = 200;
    private static final int VIDEOS_WITH_ID_CODE = 201;
    private static final int REVIEWS_CODE = 300;
    private static final int REVIEWS_WITH_ID_CODE = 301;

    //create content URIs
    public static final UriMatcher URI_MATCHER = buildMatcher();

    private static UriMatcher buildMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(FavoriteMovieContract.CONTENT_AUTHORITY, FavoriteMovieContract.FavMovieEntry.PATH_MOVIES, MOVIES_CODE);
        matcher.addURI(FavoriteMovieContract.CONTENT_AUTHORITY, FavoriteMovieContract.FavMovieEntry.PATH_MOVIES + "/#", MOVIES_WITH_ID_CODE);
        matcher.addURI(FavoriteMovieContract.CONTENT_AUTHORITY, FavoriteMovieContract.VideoEntry.PATH_VIDEOS, VIDEOS_CODE);
        matcher.addURI(FavoriteMovieContract.CONTENT_AUTHORITY, FavoriteMovieContract.VideoEntry.PATH_VIDEOS + "/#", VIDEOS_WITH_ID_CODE);
        matcher.addURI(FavoriteMovieContract.CONTENT_AUTHORITY, FavoriteMovieContract.ReviewEntry.PATH_REVIEWS, REVIEWS_CODE);
        matcher.addURI(FavoriteMovieContract.CONTENT_AUTHORITY, FavoriteMovieContract.ReviewEntry.PATH_REVIEWS + "/#", REVIEWS_WITH_ID_CODE);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        favoriteMoviesDBHelper = new FavoriteMovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        int code = URI_MATCHER.match(uri);

        switch (code) {

            case MOVIES_CODE:
                cursor = favoriteMoviesDBHelper.getReadableDatabase().query(FavoriteMovieContract.FavMovieEntry.TABLE_NAME, projection, selection, selectionArgs, null
                        , null, sortOrder);
                break;

            case MOVIES_WITH_ID_CODE:
                String movieByIdStmt = "movieId=?";
                String[] movieWhereArgs = {uri.getPathSegments().get(1)};

                cursor = favoriteMoviesDBHelper.getReadableDatabase().query(FavoriteMovieContract.FavMovieEntry.TABLE_NAME, projection, movieByIdStmt, movieWhereArgs, null, null, sortOrder);
                break;
            case VIDEOS_CODE:
                cursor = favoriteMoviesDBHelper.getReadableDatabase().query(FavoriteMovieContract.VideoEntry.TABLE_NAME, projection, selection, selectionArgs, null
                        , null, sortOrder);
                break;
            case VIDEOS_WITH_ID_CODE:
                String videoByIdStmt = "videoId=?";
                String[] videoWhereArgs = {uri.getPathSegments().get(1)};

                cursor = favoriteMoviesDBHelper.getReadableDatabase().query(FavoriteMovieContract.VideoEntry.TABLE_NAME, projection, videoByIdStmt, videoWhereArgs, null, null, sortOrder);
                break;
            case REVIEWS_CODE:
                cursor = favoriteMoviesDBHelper.getReadableDatabase().query(FavoriteMovieContract.ReviewEntry.TABLE_NAME, projection, selection, selectionArgs, null
                        , null, sortOrder);
                break;
            case REVIEWS_WITH_ID_CODE:
                String reviewByIdStmt = "videoId=?";
                String[] reviewWhereArgs = {uri.getPathSegments().get(1)};

                cursor = favoriteMoviesDBHelper.getReadableDatabase().query(FavoriteMovieContract.FavMovieEntry.TABLE_NAME, projection, reviewByIdStmt, reviewWhereArgs, null, null, sortOrder);
                break;

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = URI_MATCHER.match(uri);
        switch (code) {
            case MOVIES_CODE:
                long movie_id = favoriteMoviesDBHelper.getWritableDatabase().insert(FavoriteMovieContract.FavMovieEntry.TABLE_NAME, null, values);
                if (movie_id > 0) {
                    uri = ContentUris.withAppendedId(FavoriteMovieContract.FavMovieEntry.CONTENT_URI, movie_id);

                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);

                }
                break;

            case VIDEOS_CODE:
                long video_id = favoriteMoviesDBHelper.getWritableDatabase().insert(FavoriteMovieContract.VideoEntry.TABLE_NAME, null, values);
                if (video_id > 0) {
                    uri = ContentUris.withAppendedId(FavoriteMovieContract.VideoEntry.CONTENT_URI, video_id);

                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);

                }
                break;

            case REVIEWS_CODE:
                long review_id = favoriteMoviesDBHelper.getWritableDatabase().insert(FavoriteMovieContract.ReviewEntry.TABLE_NAME, null, values);
                if (review_id > 0) {
                    uri = ContentUris.withAppendedId(FavoriteMovieContract.ReviewEntry.CONTENT_URI, review_id);

                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);

                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI" + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);


        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = URI_MATCHER.match(uri);
        int rowsDeleted = 0;
        switch (code) {

            case MOVIES_WITH_ID_CODE:
                String deleteByIdStmt = "movieId=?";
                String[] selectWhereArgs = {uri.getPathSegments().get(1)};
                favoriteMoviesDBHelper.getWritableDatabase().delete(FavoriteMovieContract.VideoEntry.TABLE_NAME, deleteByIdStmt, selectWhereArgs);
                favoriteMoviesDBHelper.getWritableDatabase().delete(FavoriteMovieContract.ReviewEntry.TABLE_NAME, deleteByIdStmt, selectWhereArgs);
                rowsDeleted = favoriteMoviesDBHelper.getWritableDatabase().delete(FavoriteMovieContract.FavMovieEntry.TABLE_NAME, deleteByIdStmt, selectWhereArgs);
                break;
            default:
                throw new UnsupportedOperationException("URI not correct " + uri.toString());
        }
        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
