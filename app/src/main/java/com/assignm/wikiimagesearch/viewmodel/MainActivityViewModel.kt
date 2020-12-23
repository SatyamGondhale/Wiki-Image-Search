package com.assignm.wikiimagesearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assignm.wikiimagesearch.data.Repository
import com.assignm.wikiimagesearch.model.Pages

class MainActivityViewModel(application: Application):AndroidViewModel(application) {
    var vmThumbNailDataList: MutableLiveData<ArrayList<Pages>>? = null

    fun getLiveDataSearchResponse(searchQuery:String,thumb_size:Int):LiveData<ArrayList<Pages>>?{
       vmThumbNailDataList= Repository.getDataFromApi(searchQuery,thumb_size)
        return vmThumbNailDataList
    }


}
