package com.system.realtor.adapter.out.persistence

import com.system.realtor.application.port.out.AssetFindPort
import com.system.realtor.domain.Realtor

class AssetFindAdapter(
    // JPA Repo 구성
): AssetFindPort {

    override fun searchExams(pageNum: Int): List<Realtor> {

        TODO("Not yet implemented")
    }

}