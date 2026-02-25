package com.system.realtor.domain.property

 data class Price(
    val deposit: Double,        // 보증금
    val fee: Double,            // 임대료
    val sale: Double,           // 매매가
)