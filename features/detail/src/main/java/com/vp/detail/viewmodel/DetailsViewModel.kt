package com.vp.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.detail.DetailActivity
import com.vp.detail.model.MovieDetail
import com.vp.detail.service.DetailService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback
import com.google.gson.Gson
import android.preference.PreferenceManager
import com.google.gson.reflect.TypeToken
import com.vp.detail.R


class DetailsViewModel @Inject constructor(private val detailService: DetailService) : ViewModel() {

    private val details: MutableLiveData<MovieDetail> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    private val LIST_MOVIE_DETAIL = "list_movie_detail"

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    fun fetchDetails() {
        loadingState.value = LoadingState.IN_PROGRESS
        detailService.getMovie(DetailActivity.queryProvider.getMovieId()).enqueue(object : Callback, retrofit2.Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {
                details.postValue(response?.body())

                response?.body()?.title?.let {
                    title.postValue(it)
                }

                loadingState.value = LoadingState.LOADED
            }

            override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                details.postValue(null)
                loadingState.value = LoadingState.ERROR
            }
        })
    }

    fun saveDetailMovieFavorite(movieDetail: MovieDetail, detailActivity: DetailActivity) {
        var listMoviesFavorites: ArrayList<MovieDetail> = readListFavorites(detailActivity)
        listMoviesFavorites.add(movieDetail)
        saveListFavorites(listMoviesFavorites, detailActivity)
    }

    fun deleteDetailMovieFavorites(movieDetail: MovieDetail, detailActivity: DetailActivity) {
        var listMoviesFavorites: ArrayList<MovieDetail> = readListFavorites(detailActivity)
        listMoviesFavorites.remove(movieDetail)
        saveListFavorites(listMoviesFavorites, detailActivity)
    }

    fun readListFavorites(detailActivity: DetailActivity): ArrayList<MovieDetail> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(detailActivity)
        val gson = Gson()
        val json = sharedPrefs.getString(LIST_MOVIE_DETAIL, "")
        val listMovieFavoritesAsString = gson.fromJson<Any>(json,
                object : TypeToken<ArrayList<MovieDetail>>() {

                }.type)
        var arrayListMovieFavorites = ArrayList<MovieDetail>();
        if (listMovieFavoritesAsString!= null) {
            arrayListMovieFavorites = listMovieFavoritesAsString as ArrayList<MovieDetail>;
        }
        return arrayListMovieFavorites;
    }

    fun saveListFavorites(listMovieDetail: ArrayList<MovieDetail>, detailActivity: DetailActivity) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(detailActivity)
        val editor = sharedPrefs.edit()
        val gson = Gson()

        val json = gson.toJson(listMovieDetail)

        editor.putString(LIST_MOVIE_DETAIL, json)
        editor.commit()
    }


    fun isDetailMovieFavorites(movieDetail: MovieDetail, detailActivity: DetailActivity):Boolean {
        var listMoviesFavorites: ArrayList<MovieDetail> = readListFavorites(detailActivity)
        return listMoviesFavorites.contains(movieDetail)

    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}