package com.alkemy.ongsomosmas.data.member

import com.alkemy.ongsomosmas.data.BaseDataSource
import javax.inject.Inject

class MemberDataSource @Inject constructor(
    private val memberService: MemberService
) : BaseDataSource (){
    suspend fun getMember() = getResult { memberService.fetchMembers() }
}