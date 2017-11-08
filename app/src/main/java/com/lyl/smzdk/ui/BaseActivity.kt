package com.lyl.smzdk.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Author: lyl
 * Date Created : 2017/11/2.
 */
open class BaseActivity : AppCompatActivity() {

    lateinit var mContent: Context
    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContent = this
        mActivity = this

    }

//    fun setMyActionBar(resId: Int) {
//        val actionBar = findViewById<ActionBar>(R.id.actionbar)
//        actionBar.setModelBack(resId, mActivity)
//    }

    fun showT(resId: Int) {
        Toast.makeText(applicationContext, resId, Toast.LENGTH_SHORT).show()
    }

    fun showT(resId: String) {
        Toast.makeText(applicationContext, resId, Toast.LENGTH_SHORT).show()
    }

}
