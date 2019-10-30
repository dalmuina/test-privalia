package com.vp.favorites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vp.favorites.model.MovieFavorite;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private ArrayList<MovieFavorite> mData;
    private LayoutInflater mInflater;
    private Context context;

    FavoriteAdapter(Context context, ArrayList<MovieFavorite> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieFavorite movieFavorite = mData.get(position);
        holder.title.setText(movieFavorite.getTitle());
        Picasso.get()
                .load(movieFavorite.getPoster())
                .into(holder.poster);
        holder.year.setText(movieFavorite.getYear());
        holder.director.setText(movieFavorite.getDirector());
        holder.runtime.setText(movieFavorite.getRuntime());
        holder.plot.setText(movieFavorite.getPlot());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView poster;
        TextView year;
        TextView director;
        TextView runtime;
        TextView plot;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            poster = itemView.findViewById(R.id.poster);
            year = itemView.findViewById(R.id.year);
            director = itemView.findViewById(R.id.director);
            runtime = itemView.findViewById(R.id.runtime);
            plot = itemView.findViewById(R.id.plot);
        }

        @Override
        public void onClick(View view) {

        }
    }

    MovieFavorite getItem(int id) {
        return mData.get(id);
    }

}
