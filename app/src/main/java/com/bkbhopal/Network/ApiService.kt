package com.example.bkbhopal.Network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @POST("1FAIpQLSexzEB4vB6oBUDajvcdmpn1Phzn61Jauug2QBYUJjirMSCtSw/formResponse")
    @FormUrlEncoded
    fun submitResponse(
        @Field("entry.167627252") emailAddress: String,
        @Field("entry.1487586230") fullName: String,
        @Field("entry.1673881430") github: String,
        @Field("entry.2059565087") gender: String,
        @Field("entry.124829766") kotlin: String?,
        @Field("entry.124829766") java: String?,
        @Field("entry.124829766") dart: String?,
        @Field("entry.124829766") swift: String?,
    ): Call<Void>

    @GET("v1/forms/1FAIpQLSexzEB4vB6oBUDajvcdmpn1Phzn61Jauug2QBYUJjirMSCtSw")
    fun getResponse(): Call<ResponseBody?>?



    @GET
    open fun queryparamsPostApiCall(@Url apiName: String?): Call<ResponseBody?>?
}