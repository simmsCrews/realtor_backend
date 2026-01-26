package com.system.realtor.adapter.`in`

import com.system.realtor.application.port.`in`.AssetFindUsecase
import com.system.realtor.supoort.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RealtorController(
    private val assetFindUsecase: AssetFindUsecase
) {
    @GetMapping("/exams")
    fun getExams(
        @RequestParam(required = false) pageNum: Int
    ): Response<Void> {

        // 호출

        return Response.success();
    }
}