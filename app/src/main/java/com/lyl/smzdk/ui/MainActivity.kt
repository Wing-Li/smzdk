package com.lyl.smzdk.ui

import android.os.Bundle
import com.lyl.smzdk.R
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.OnTabReselectListener
import com.roughike.bottombar.OnTabSelectListener

class MainActivity : BaseActivity() {

    lateinit var mBottomBar: BottomBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomBar();
    }

    fun initBottomBar() {
        mBottomBar = findViewById(R.id.bottomBar)

        mBottomBar.setOnTabSelectListener(OnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tab_news -> { // 资讯

                }
                R.id.tab_video -> { // 视频

                }
                R.id.tab_link-> { // 搜索

                }
                R.id.tab_shop -> { // 购物

                }
                R.id.tab_user -> { // 用户

                }
            }
        })

        mBottomBar.setOnTabReselectListener(OnTabReselectListener { tabId ->
        })
    }
}
