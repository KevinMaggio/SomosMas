package com.alkemy.ongsomosmas.ui.aboutus

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.member.MemberRepository
import com.alkemy.ongsomosmas.data.model.MemberResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AboutUsViewModel @ViewModelInject constructor(
    private val memberRepository: MemberRepository,
) : ViewModel() {
    private val _memberResult = MutableLiveData<Resource<List<MemberResponse>>>()
    val memberResult: LiveData<Resource<List<MemberResponse>>> = _memberResult

    init {
        getMemberResponse()
    }

    fun getMemberResponse() = viewModelScope.launch(Dispatchers.Main){
       _memberResult.value = Resource.loading()
        val result = withContext(Dispatchers.IO) {

            memberRepository.members()
        }
        _memberResult.value = result
    }
}