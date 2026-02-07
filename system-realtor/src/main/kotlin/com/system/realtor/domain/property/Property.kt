package com.system.realtor.domain.property

import java.time.LocalDate

import com.system.realtor.domain.Realtor

/**
   매물
 */
class Property(
   val residentialType: ResidentialType,     // 주거형태
   val building: String,                     // 동
   val unit: String,                         // 호
   val price: String,                        // 가격
   val status: String,                        // 상태
   val area: String,                          // 평형
   val contractEndDate: LocalDate,           // 만기일
   val landLoad: String,                      // 임대(매도)인
   val realtor: Realtor
) {

}