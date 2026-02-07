package com.system.realtor.domain.property

data class Address(
    val post :String,
    val address1: String,
    val address2: String,
    val detail: String,
    val residentialType :ResidentialType,    // 주거 형태
    val area: String,
//    val latitude : Long,    //위도  지도 api 연동시
//    val longitude: Long,    //경도

)
