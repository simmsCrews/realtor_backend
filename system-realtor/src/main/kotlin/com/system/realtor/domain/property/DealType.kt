package com.system.realtor.domain.property

enum class DealType(
    val code: String,
    val description: String,
) {
    SALE("Sale", "매매"),
    FULL_RENT("FullRent", "전세"),
    LEASE("Lease", "임대"),
}