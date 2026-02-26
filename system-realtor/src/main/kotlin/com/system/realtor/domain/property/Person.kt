package com.system.realtor.domain.property

/**
  소유주
 * */
class Person(
    val name:String,
    val mobile: String,
    val phone: String,
    val address: Address,
    val registryNumber:String,
    val description: String
){}
