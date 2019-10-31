package com.vp.detail

import android.app.PendingIntent.getActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.model.MovieDetail
import com.vp.detail.viewmodel.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.run

class DetailActivity : DaggerAppCompatActivity(), QueryProvider {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var movieDetail: MovieDetail;
    private lateinit var detailViewModel: DetailsViewModel;
    private lateinit var menu: Menu;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        detailViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        binding.viewModel = detailViewModel
        queryProvider = this
        binding.setLifecycleOwner(this)
        detailViewModel.fetchDetails()
        detailViewModel.title().observe(this, Observer {
            supportActionBar?.title = it
        })
        detailViewModel.details().observe(this, Observer {
            movieDetail = it
            menu?.getItem(0)?.setChecked(isDetailMovieFavorite())
            if (isDetailMovieFavorite()) {
                menu?.getItem(0)?.setIcon(R.drawable.ic_star_selected);
            } else {
                menu?.getItem(0)?.setIcon(R.drawable.ic_star);
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu!!
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.star -> {
                if (!item.isChecked) {
                    item.setIcon(R.drawable.ic_star_selected)
                    saveDetailMovieFavorite();
                } else {
                    item.setIcon(R.drawable.ic_star);
                    deleteDetailMovieFavorite();
                }
                item.setChecked(!item.isChecked);
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun isDetailMovieFavorite(): Boolean {
        return detailViewModel.isDetailMovieFavorites(movieDetail, this);
    }

    fun saveDetailMovieFavorite(){
        detailViewModel.saveDetailMovieFavorite(movieDetail,this)
    }

    fun deleteDetailMovieFavorite(){
        detailViewModel.deleteDetailMovieFavorites(movieDetail,this)
    }

    override fun getMovieId(): String {
        return intent.getStringExtra("imdbID") ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    companion object {
        lateinit var queryProvider: QueryProvider
    }
}
