package com.example.tft_flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.tft_flixster.DetailActivity;
import com.example.tft_flixster.MainActivity;
import com.example.tft_flixster.R;
import com.example.tft_flixster.models.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;
    private final int POPULAR = 1, NOT_POPULAR = 0;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public int getItemViewType(int position){
        if (movies.get(position).getRating() >= 8)
            return POPULAR;
        else
            return NOT_POPULAR;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView;
        ViewHolder viewHolder;
        if (viewType == POPULAR ) {
            movieView = LayoutInflater.from(context).inflate(R.layout.viewholder_popular, parent, false);
            viewHolder = new ViewHolder(movieView);
        }
        else {
            movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(movieView);
        }
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public static final String YOUTUBE_API_KEY = "AIzaSyAldOKh_BbjLmlldSG5_d2OEWDhwONkdFI";
        public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivBackdrop;
        FloatingActionButton fabPopPlay;
        YouTubePlayerView player;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvOverview.setMovementMethod(new ScrollingMovementMethod());
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            ivBackdrop = itemView.findViewById(R.id.ivBackdrop);
            fabPopPlay = itemView.findViewById(R.id.fabPopPlay);
            player = itemView.findViewById(R.id.player);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl = movie.getPosterPath();
            String backdropUrl = movie.getBackdropPath();

            if (getItemViewType() == POPULAR) {
                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // stuff
                } else {
                    Glide.with(context)
                            .load(backdropUrl)
                            .into(ivBackdrop);
                }
            } else {
                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // stuff
                } else {
                    Glide.with(context)
                            .load(imageUrl)
                            .transform(new FitCenter(), new RoundedCorners(25))
                            .into(ivPoster);
                    Glide.with(context)
                            .load(backdropUrl)
                            .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,3)))
                            .into(ivBackdrop);
                }
            }

//                Glide.with(context)
//                        .load(imageUrl)
//                        //.transform(new FitCenter(), new RoundedCorners(25))
//                        //.apply(RequestOptions.bitmapTransform(new BlurTransformation(7,1)))
//                        .into(ivPoster);

            // ORIGINAL - Glide.with(context).load(imageUrl).into(ivPoster);

            // 1. Register click listener on the whole row
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((Activity) context, tvTitle, "profile");
//                    context.startActivity(i, options.toBundle());
                    context.startActivity(i);
                }
            });
            tvOverview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((Activity) context, tvTitle, "profile");
//                    context.startActivity(i, options.toBundle());
                    context.startActivity(i);
                }
            });
        }
    }
}
