package com.system.realtor.supoort

data class Response<T>(
    val resultCode: String,
    val data: T?
) {
    companion object {
        fun <T> success() : Response<T> = Response("SUCCESS", null)
        fun <T> success(result: T) : Response<T> = Response("SUCCESS", result)
        fun <T> error(resultCode: T) : Response<T> = Response("ERROR", resultCode)
        fun <T> error(resultCode: String, message: T) : Response<T> = Response(resultCode, message)
    }
}