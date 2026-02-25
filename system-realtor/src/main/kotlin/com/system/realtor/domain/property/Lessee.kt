package com.system.realtor.domain.property


/**
 * 임차인
 * */
class Lessee(
    val name:String,
    val mobile: String,
    val phone: String,
    val address: Address,
    val registryNumber:String,
    val description: String
) {


}