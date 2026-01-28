package com.system.realtor.domain.property

enum class PropertyType(
    val code: String,
    val description: String,
) {
    APARTMENT("APARTMENT", "아파트"),
    VILLA("VILLA", "빌라"),


}