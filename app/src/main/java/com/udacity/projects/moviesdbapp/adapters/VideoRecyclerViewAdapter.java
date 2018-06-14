package com.udacity.projects.moviesdbapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.projects.moviesdbapp.R;
import com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.model.Video;
import com.udacity.projects.moviesdbapp.util.MovieUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoViewHolder> {

    private List<Video> videoData = new ArrayList<>();
    private Context context;
    private LayoutInflater videoInflater;
    private VideoRecyclerViewAdapter.ItemClickListener vClickListener;

    // data is passed into the constructor
    public VideoRecyclerViewAdapter(Context context,List<Video> data , VideoRecyclerViewAdapter.ItemClickListener itemClickListener ) {
        this.videoInflater = LayoutInflater.from(context);
        this.videoData = data;
        this.context = context;
        this.vClickListener = itemClickListener;
    }
    @Override
    @NonNull
    public VideoRecyclerViewAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = videoInflater.inflate(R.layout.video_recycleview_item, parent, false);
        return new VideoRecyclerViewAdapter.VideoViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(@NonNull VideoRecyclerViewAdapter.VideoViewHolder holder, int position) {
        Video video = videoData.get(position);
        holder.bind(video);
    }

    // total number of cells
    @Override
    public int getItemCount() {

        return videoData != null ? videoData.size() : 0;
    }
    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageButton myVideoButton;
        TextView textView;


        VideoViewHolder(View itemView) {
            super(itemView);
            myVideoButton = (ImageButton) itemView.findViewById(R.id.ib_video);
            textView = (TextView) itemView.findViewById(R.id.tv_video);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (vClickListener != null) {
                vClickListener.onItemClick(view, getAdapterPosition());

            }
        }

        void bind(Video video)
         {
             int imgRes = R.drawable.ic_launcher_background;
             if (!video.getType().equals("Trailer")) {
                 imgRes = R.drawable.ic_clip;
             }
             myVideoButton.setImageResource(imgRes);
             textView.setText(video.getName());
             itemView.setTag(video);

        }
    }

    // allows clicks events to be caught
    public void setClickListener(VideoRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.vClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
