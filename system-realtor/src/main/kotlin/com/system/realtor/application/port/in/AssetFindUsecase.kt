package com.system.realtor.application.port.`in`

import com.system.realtor.domain.Realtor

interface AssetFindUsecase {

    fun searchExams(pageNum: Int): List<Realtor>

}