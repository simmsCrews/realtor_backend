package com.system.realtor.domain.property

import java.time.LocalDate

import com.system.realtor.domain.Realtor
import java.util.Properties

/**
   매물
 */
class Property(

   // 계약 객체
   val contractEndDate: LocalDate,           // 만기일
   val price: Price,                         // 가격
   val status: String,                       // 상태

   // 희망 거래 종류
   val dealType: Set<DealType>,

   // 권리당사자
   val owner: Owner,                         // 소유주
   val landLoad: Landlord,                   // 임대인
   val lessee: Lessee,                       // 임차인

   //주소 vo
   val address: Address,                     // 주소

   val realtor: Realtor
) {


}