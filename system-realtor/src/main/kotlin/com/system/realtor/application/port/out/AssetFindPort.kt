package com.system.realtor.application.port.out

import com.system.realtor.domain.Realtor

interface AssetFindPort {

    fun searchExams(pageNum: Int): List<Realtor>

}