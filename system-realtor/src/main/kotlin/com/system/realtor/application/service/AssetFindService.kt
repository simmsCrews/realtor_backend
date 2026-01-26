package com.system.realtor.application.service

import com.system.realtor.application.port.`in`.AssetFindUsecase
import com.system.realtor.domain.Realtor
import org.springframework.stereotype.Service

@Service
class AssetFindService(): AssetFindUsecase {

//    private assetFindPort: AssetFindPort

    override fun searchExams(pageNum: Int): List<Realtor> {

        TODO("Not yet implemented")

    }

}