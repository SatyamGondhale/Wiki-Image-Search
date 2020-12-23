package com.assignm.wikiimagesearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assignm.wikiimagesearch.data.Repository
import com.assignm.wikiimagesearch.model.Pages
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application):AndroidViewModel(application) {
    var vmThumbNailDataList: MutableLiveData<ArrayList<Pages>>? = null
    var repository:Repository?=null

    init {
        repository=Repository()
    }
    fun getLiveDataSearchResponse(searchQuery:String,thumb_size:Int):LiveData<ArrayList<Pages>>?{
        viewModelScope.launch {
            vmThumbNailDataList= repository?.getDataFromApi(searchQuery,thumb_size)
        }
        return vmThumbNailDataList
    }

}
