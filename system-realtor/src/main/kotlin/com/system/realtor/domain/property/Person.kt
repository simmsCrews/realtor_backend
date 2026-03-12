package com.system.realtor.domain.property

/**
    임대인, 임차인 정보
 **/
class Person(
    val name:String,
    val mobile: String,
    val phone: String,
    val address: Address,
    val registryNumber:String,
    val description: String
){}
