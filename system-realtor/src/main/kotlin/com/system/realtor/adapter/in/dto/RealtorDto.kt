package com.system.realtor.adapter.`in`.dto

import com.system.realtor.domain.Realtor

class RealtorDto {

    data class Response(
        val realtorId: Long,
        val name: String,
    ) {
        companion object {
            fun of(
                realtorId: Long, name: String
            ): Response = Response(realtorId, name)

            fun from(realtor: Realtor): Response =
                of(realtorId = realtor.realtorId, name = realtor.name)
        }
    }

}