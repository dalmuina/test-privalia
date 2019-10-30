package com.vp.favorites.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieFavorite implements Serializable {
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @SerializedName("Runtime")
    private String runtime;
    @SerializedName("Director")
    private String director;
    @SerializedName("Plot")
    private String plot;
    @SerializedName("Poster")
    private String poster;

    public MovieFavorite() {
    }

    public MovieFavorite(String title, String year, String runtime, String director, String plot, String poster) {
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.director = director;
        this.plot = plot;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
