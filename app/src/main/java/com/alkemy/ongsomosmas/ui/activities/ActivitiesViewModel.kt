package com.alkemy.ongsomosmas.ui.activities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.activities.ActivitiesRepository
import com.alkemy.ongsomosmas.data.model.ActivitiesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivitiesViewModel @ViewModelInject constructor(
    private val activitiesRepository: ActivitiesRepository
) : ViewModel() {

    private val _activitiesResult = MutableLiveData<Resource<List<ActivitiesResponse>>>()
    val activitiesResult: LiveData<Resource<List<ActivitiesResponse>>> = _activitiesResult

    init {
        getActivitiesResult()
    }

    fun getActivitiesResult() = viewModelScope.launch(Dispatchers.Main) {
        _activitiesResult.value = Resource.loading()
        val result = withContext(Dispatchers.IO) {
            activitiesRepository.activities()
        }
        _activitiesResult.value = result
    }
}