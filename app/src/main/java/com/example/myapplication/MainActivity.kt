package com.example.myapplication

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.myapplication.adapter.ResponseAdapter
import com.example.myapplication.apiservices.MyResponse
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.CONNECTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.view.KeyEvent
import android.view.MenuItem
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.app.Activity
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity(), View.OnClickListener, com.example.myapplication.View {

    private var responseAdapter: ResponseAdapter? = null
    private var presenter: Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()
        setListeners()
        setToolbarTitle("Github")
        backButtonEnable(false)
    }

    private fun initialization() {
        presenter = Presenter(applicationContext, this)
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                responseAdapter?.filter?.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun setListeners() {
        btnUserNames.setOnClickListener(this)
        etUserNamesearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                callApi()
                hideKeyboardFrom(this,v)
                return@OnEditorActionListener true
            }
            false
        })
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUserNames -> {
               callApi()
            }
        }
    }

    private fun callApi() {
        val userName = etUserNamesearch.text.toString()
        if (userName.isNotEmpty()) {
            if (isNetworkConnected()) {
                presenter?.callApi(userName)
            } else {
                showErrorMessage("Please Check your internet connection")
            }
        } else {
            showErrorMessage("Please enter user name to get the projects")
        }
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showData(useName:String,body: ArrayList<MyResponse>) {
        setToolbarTitle("Github/$useName")
        backButtonEnable(true)
        groupProjects.visibility = View.VISIBLE
        groupUserNames.visibility = View.GONE
        rcv_results.layoutManager = LinearLayoutManager(this)
        responseAdapter = ResponseAdapter(this, body)
        rcv_results.adapter = responseAdapter
    }

    override fun showProgress() {
        groupProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        groupProgressbar.visibility = View.GONE
    }

    override fun onBackPressed() {

        if (groupUserNames.visibility == View.VISIBLE) {
            super.onBackPressed()
        } else {
            setToolbarTitle("Github")
            backButtonEnable(false)
            groupUserNames.visibility = View.VISIBLE
            groupProjects.visibility = View.GONE
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    private fun setToolbarTitle(name: String) {
        supportActionBar?.title = name
    }
    private fun backButtonEnable(enable:Boolean){
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
        supportActionBar?.setDisplayShowHomeEnabled(enable)
    }
}
