package com.system.realtor.domain.property

/**
  임대인
 * */
class Landlord(
    val name:String,
    val mobile: String,
    val phone: String,
    val address: Address,
    val registryNumber:String,
    val description: String
){}
