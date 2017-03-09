package com.wassim.mvvmboilerplate.ui.views.main;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wassim.mvvmboilerplate.R;
import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.databinding.ItemMovieBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wassim on 3/8/17.
 */

class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    private List<Movie> movieList = new ArrayList<>();

    public MoviesAdapter() {
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding itemMovieBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_movie,
                        parent, false);
        return new MovieHolder(itemMovieBinding);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        holder.bindMovie(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMovies(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    static class MovieHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding mItemPeopleBinding;

        MovieHolder(ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.itemContainer);
            this.mItemPeopleBinding = itemMovieBinding;
        }

        void bindMovie(Movie movie) {
            if (mItemPeopleBinding.getViewModel() == null) {
                mItemPeopleBinding.setViewModel(
                        new ItemMovieViewModel(movie));
            } else {
                mItemPeopleBinding.getViewModel().setMovie(movie);
            }
        }
    }

}
