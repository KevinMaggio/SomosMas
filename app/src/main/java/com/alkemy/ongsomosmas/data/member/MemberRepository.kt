package com.alkemy.ongsomosmas.data.member

import javax.inject.Inject

class MemberRepository @Inject constructor(
    private val memberDataSource: MemberDataSource){
    suspend fun members() = memberDataSource.getMember()
}