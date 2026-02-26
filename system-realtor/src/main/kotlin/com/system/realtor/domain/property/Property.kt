package com.system.realtor.domain.property

import java.time.LocalDate

import com.system.realtor.domain.Realtor

/**
   매물
 */
class Property(

   // 조건
   val price: Price,                         // 가격
   // todo 면적 vo 추가

   // 희망 거래 종류
   val dealType: Set<DealType>,

   // 권리 당사자
   val person: Person,                         // 소유주

   // 주소 vo
   val address: Address,                     // 주소

   // 특이 사항 : enum


   // 메모
   val memo: String

) {


}