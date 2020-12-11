package com.sicoapp.movieapp.ui.movie.crew.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sicoapp.movieapp.BR
import com.sicoapp.movieapp.databinding.ItemCrewBinding
import com.sicoapp.movieapp.databinding.ItemMovieBinding

class ViewHolder(val itemCrewBinding: ItemCrewBinding) :
    RecyclerView.ViewHolder(itemCrewBinding.root) {
    fun bind(obj: Any?) {
        itemCrewBinding.setVariable(BR.data, obj)
        itemCrewBinding.executePendingBindings()
    }
}