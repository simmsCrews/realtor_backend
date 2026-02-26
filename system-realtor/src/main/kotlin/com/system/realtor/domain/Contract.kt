package com.system.realtor.domain

import com.system.realtor.domain.property.Person
import com.system.realtor.domain.property.Property
import java.time.LocalDate

class Contract(
    val contractEndDate: LocalDate,           // 만기일
    val status: String,                       // 상태
    val landLoad: Person,                     // 임대인
    val lessee: Person,                       // 임차인
    val property: Property                    // 매물
) {
}