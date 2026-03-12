package com.system.realtor.domain

import com.system.realtor.domain.property.Address

data class Office(
    val name: String,
    val phone: String,
    val address: Address,
    val registerNumber: String,
) {
}