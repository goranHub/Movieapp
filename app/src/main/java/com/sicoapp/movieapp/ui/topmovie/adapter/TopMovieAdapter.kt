package com.sicoapp.movieapp.ui.topmovie.adapter

/**
 * @author ll4
 * @date 12/10/2020
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sicoapp.movieapp.R
import com.sicoapp.movieapp.databinding.ItemMovieBinding
import com.sicoapp.movieapp.ui.popular.BindMovie
import com.sicoapp.movieapp.ui.topmovie.TopMovieCallback

class TopMovieAdapter : RecyclerView.Adapter<ViewHolder>() {

    lateinit var callback: TopMovieCallback

    var list = mutableListOf<BindMovie>()

    lateinit var binding: ItemMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = list[position]
        holder.bind(dataModel)

        //bind listener implementation with listener that are in layout
        //pass the clicked id to the openDetails callback
        binding.itemClickListener = object : ItemClickListener {
            override fun openItem(itemId: Long) {
                callback.openDetails(itemId)
            }
        }

        binding.itemCrewClickListener = object : ItemClickListener {
            override fun openItem(itemId: Long) {
                callback.openCrew(itemId)
            }
        }
    }

    override fun getItemCount() = list.size

    fun addMovies(listItems: List<BindMovie>) {
        list.addAll(listItems)
        notifyDataSetChanged()
    }
}