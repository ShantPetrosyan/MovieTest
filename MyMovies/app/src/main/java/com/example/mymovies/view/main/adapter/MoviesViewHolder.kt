package com.example.mymovies.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovies.R
import com.example.mymovies.data.model.movielist.MovieInfo
import com.example.mymovies.data.net.ApiConstants
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(user: MovieInfo?, itemClickedCallBack: (item: MovieInfo) -> Unit) {
        itemView.txtTitle.text = itemView.context.getString(R.string.title, user?.title)
        itemView.txtRate.text = itemView.context.getString(R.string.rate, user?.voteAverage.toString())
        itemView.txtYear.text = itemView.context.getString(R.string.year, user?.releaseDate)

        Glide.with(itemView.context)
            .load(ApiConstants.IMAGE_HOST + "/" + ApiConstants.IMAGE_SIZE_SMALL + user?.posterPath)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(itemView.imgMovie)

        itemView.lnItem.setOnClickListener{
            user?.let(itemClickedCallBack)
        }
    }

    companion object {
        fun create(parent: ViewGroup): MoviesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
            return MoviesViewHolder(view)
        }
    }
}