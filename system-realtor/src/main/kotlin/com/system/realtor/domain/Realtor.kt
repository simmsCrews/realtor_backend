package com.system.realtor.domain

import com.system.realtor.domain.property.Property

/**
    공인중개사
 */
class Realtor(
    val id: Long,

    val info: RealtorInfo,

    val agents: List<Agent>,
    val properties: List<Property>
) {

}