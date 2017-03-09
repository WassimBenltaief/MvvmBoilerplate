package com.wassim.mvvmboilerplate.ui.views.main;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wassim.mvvmboilerplate.R;
import com.wassim.mvvmboilerplate.data.model.Movie;

import static com.wassim.mvvmboilerplate.util.Vars.PLACEHOLDER;

/**
 * Created by wassim on 3/8/17.
 */

public class ItemMovieViewModel extends BaseObservable {

    private Movie movie;

    ItemMovieViewModel(Movie movie) {
        this.movie = movie;
    }

    public String getTitle() {
        return movie.title();
    }

    public String getGenre() {
        return movie.genre();
    }

    public String getUrl(){
        return movie.image().isEmpty() ? PLACEHOLDER : movie.image();
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        notifyChange();
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.color.colorAccent)
                .into(imageView);
    }
}
