package com.vp.favorites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vp.favorites.model.MovieFavorite;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private FavoriteAdapter adapter;
    private static String LIST_MOVIE_DETAIL = "list_movie_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        RecyclerView recyclerView = findViewById(R.id.rvFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteAdapter(this, getMovieFavorites());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<MovieFavorite> getMovieFavorites() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(LIST_MOVIE_DETAIL, null);
        Type type = new TypeToken<ArrayList<MovieFavorite>>() {
        }.getType();
        ArrayList<MovieFavorite> arrayList = gson.fromJson(json, type);
        return arrayList;
    }
}
