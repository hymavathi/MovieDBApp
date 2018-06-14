package com.udacity.projects.moviesdbapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.udacity.projects.moviesdbapp.R;
import com.udacity.projects.moviesdbapp.model.Review;
import com.udacity.projects.moviesdbapp.model.Video;

import java.util.ArrayList;
import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {

    private List<Review> reviews = new ArrayList<>();
    private Context context;
    private LayoutInflater reviewInflater;
    private ReviewRecyclerViewAdapter.ItemClickListener rClickListener;

    // data is passed into the constructor
    public ReviewRecyclerViewAdapter(Context context, List<Review> data, ReviewRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.reviewInflater = LayoutInflater.from(context);
        this.reviews = data;
        this.context = context;
        this.rClickListener = itemClickListener;
    }

    @Override
    @NonNull
    public ReviewRecyclerViewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = reviewInflater.inflate(R.layout.review_recycleview_item, parent, false);
        return new ReviewRecyclerViewAdapter.ReviewViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerViewAdapter.ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    // total number of cells
    @Override
    public int getItemCount() {

        return reviews != null ? reviews.size() : 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView reviewNumber;
        TextView reviewAuthor;
        TextView reviewContent;


        ReviewViewHolder(View itemView) {
            super(itemView);
            reviewNumber = (TextView) itemView.findViewById(R.id.tv_review_sequence);
            reviewAuthor = (TextView) itemView.findViewById(R.id.tv_review_author);
            reviewContent = (TextView) itemView.findViewById(R.id.tv_review_comment);
        }

        @Override
        public void onClick(View view) {
            if (rClickListener != null) {
                rClickListener.onItemClick(view, getAdapterPosition());

            }
        }

        void bind(Review review) {
            int i = 1;
            reviewNumber.setText(String.valueOf(i));
            reviewAuthor.setText(review.getAuthor());
            reviewContent.setText(review.getContent());
            i++;

        }
    }

    // allows clicks events to be caught
    public void setClickListener(ReviewRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.rClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
