package com.example.myapplication

import android.content.Context

import com.example.myapplication.apiservices.MyResponse
import com.example.myapplication.apiservices.Rest
import com.example.myapplication.apiservices.RestInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Presenter(private var context: Context, private var view: View) {

    fun callApi(userName: String) {
        view.showProgress()
        buildRetrioFit(Rest.API_BASE).contribution(userName).enqueue(object : Callback<ArrayList<MyResponse>> {
            override fun onFailure(call: Call<ArrayList<MyResponse>>, t: Throwable) {
                view.hideProgress()
                view.showErrorMessage(t.message ?: "Opps Some Error!")
            }

            override fun onResponse(
                call: Call<ArrayList<MyResponse>>,
                response: retrofit2.Response<ArrayList<MyResponse>>
            ) {
                view.hideProgress()
                val myResponse = response.body()
                if (myResponse != null) {
                    if (myResponse.size > 0) {
                        view.showData(userName,myResponse)
                    } else {
                        view.showErrorMessage("No data to show")
                    }
                } else {
                    view.showErrorMessage("We are getting null body")
                }
            }
        })
    }

    /**
     * Build Retriofit object
     */
    fun buildRetrioFit(apiName: String): RestInterface {
        val retrofit = Retrofit.Builder().baseUrl(apiName)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(RestInterface::class.java)
    }

}