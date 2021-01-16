package com.sicoapp.movieapp.data.repository

import androidx.lifecycle.LiveData
import com.sicoapp.movieapp.data.database.DAOAccess
import com.sicoapp.movieapp.data.database.SmileyRatingTableModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author ll4
 * @date 12/15/2020
 */

class SmileyRepository @Inject constructor(
    val dao: DAOAccess
) {

    private var smileyRatingTableModel: LiveData<SmileyRatingTableModel>? = null
    private var smileyRatingTableModelList: LiveData<List<SmileyRatingTableModel>>? = null

    fun insertData(itemId: Int, rating: Int) {
        CoroutineScope(IO).launch {
            val movieRatingDetails = SmileyRatingTableModel(itemId, rating)
            dao.insert(movieRatingDetails)
        }
    }

    suspend fun getSaved(): List<SmileyRatingTableModel> {
        return dao.getSaved()
    }

    fun getMovieRatingDetails(itemId: Int): LiveData<SmileyRatingTableModel> {
        smileyRatingTableModel = dao.loadById(itemId)
        return smileyRatingTableModel as LiveData<SmileyRatingTableModel>
    }

    fun removeDataForThatItem(itemId: Int) {
        CoroutineScope(IO).launch {
            dao.deleteByID(itemId)
        }
    }
}