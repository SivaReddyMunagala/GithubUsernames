package com.example.myapplication.apiservices

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

interface RestInterface {

    @GET("{userName}/repos")
    fun contribution(@Path("userName") userName:String): Call<ArrayList<MyResponse>>

}