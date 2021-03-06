package com.udacity.projects.moviesdbapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udacity.projects.moviesdbapp.R;
import com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.util.MovieUtil;

import java.util.ArrayList;
import java.util.List;


public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private List<Movie> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MovieRecyclerViewAdapter(Context context,List<Movie> data , ItemClickListener itemClickListener ) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.mClickListener = itemClickListener;
    }


    @Override
    @NonNull
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_item, parent, false);
        return new MovieViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mData.get(position);
        holder.myImageView.setImageURI(Uri.parse(movie.getPosterPath()));
        holder.bind(movie);
    }

    // total number of cells
    @Override
    public int getItemCount() {

        return mData != null ? mData.size() : 0;
    }


    // stores and recycles views as they are scrolled off screen
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImageView;

        MovieViewHolder(View itemView) {
            super(itemView);
            myImageView = (ImageView) itemView.findViewById(R.id.movie_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());

            }
        }

         void bind(Movie movie) {
            MovieUtil.loadImage(context, movie.getPosterPath(), myImageView);

        }
    }

    // convenience method for getting data at click position
    Movie getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
