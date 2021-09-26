package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;

    private final int NOTPOP = 0, POP = 1;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case NOTPOP:
                View movieView1 = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder1(movieView1);
                break;
            case POP:
                View movieView2 = LayoutInflater.from(context).inflate(R.layout.item_pop_movie, parent, false);
                viewHolder = new ViewHolder2(movieView2);
                break;
            default:
                View movieView3 = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder1(movieView3);
                break;
        }
        return viewHolder;
    }

    // Involves populating that data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder");
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        //Bind the movie data into the VH
        switch (viewHolder.getItemViewType()) {
            case NOTPOP:
                ViewHolder1 vh1 = (ViewHolder1) viewHolder;
                vh1.bind(movie);
                break;
            case POP:
                ViewHolder2 vh2 = (ViewHolder2) viewHolder;
                vh2.bind(movie);
                break;
            default:
                ViewHolder1 vh3 = (ViewHolder1) viewHolder;
                vh3.bind(movie);
                break;
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        //voteAverage
        if (movies.get(position).getRating()<8) {
            return NOTPOP;
        } else if (8<=movies.get(position).getRating()) {
            return POP;
        }
        return -1;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
            }
            else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }

            int radius = 60; // corner radius, higher value = more rounded
            int margin = 20; // crop margin, set to 0 for corners with no crop
            Glide.with(context)
                    .load(imageUrl)
                    .circleCrop() // scale image to fill the entire ImageView
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);

            // 1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        RelativeLayout container;
        ImageView ivPoster;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            String imageUrl;
            imageUrl = movie.getBackdropPath();

            int radius = 60; // corner radius, higher value = more rounded
            int margin = 20; // crop margin, set to 0 for corners with no crop
            Glide.with(context)
                    .load(imageUrl)
                    .circleCrop() // scale image to fill the entire ImageView
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);

            // 1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
