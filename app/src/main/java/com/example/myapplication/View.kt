package com.example.myapplication

import com.example.myapplication.apiservices.MyResponse

interface View {
     fun showErrorMessage(message: String)
     fun showData(userName:String,body: ArrayList<MyResponse>)
    fun showProgress()
    fun hideProgress()
}