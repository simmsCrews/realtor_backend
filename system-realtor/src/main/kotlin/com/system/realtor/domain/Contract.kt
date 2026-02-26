package com.system.realtor.domain

import com.system.realtor.domain.property.Document
import com.system.realtor.domain.property.Person
import com.system.realtor.domain.property.Property
import com.system.realtor.domain.property.Term
import java.time.LocalDate

class Contract(
    val id: Long,

    val contractEndDate: LocalDate,           // 만기일
    val status: String,                       // 상태
    val provider: Person,                     // 임대인
    val customer: Person,                     // 임차인
    val property: Property,                   // 매물

    val document: Document,                     // 계약내용
    val terms: List<Term>,                      // 특약사항
    val memo: String,                            // 메모

    val listingRealtor: RealtorInfo,            // 물건지 중개사 정보
    val cooperatingRealtor: RealtorInfo,        // 임차인 중개사 정보
) {

}