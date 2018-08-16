package com.lyl.smzdk.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.lyl.smzdk.R
import com.lyl.smzdk.utils.ImgUtils
import com.lyl.smzdk.utils.MyUtils
import com.lyl.smzdk.utils.SPUtil
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*


class SplashActivity : BaseActivity() {

    companion object {
        // App 第一次启动
        val isAppFirstRunning: String = "isFirstRunning";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 判断 App 是否是第一次启动
        val isAppFirstRunning = SPUtil.get(applicationContext, isAppFirstRunning, true) as Boolean
        if (isAppFirstRunning) {
            SPUtil.put(applicationContext, Companion.isAppFirstRunning, false)

            firstRunningInit()

        } else {
            if (!MyUtils.isDev()) {
                try {
                    Thread.sleep(1500)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            goMainActivity()
        }
    }

    /**
     * App 第一次启动时，初始化滑动页面
     */
    private fun firstRunningInit(){
        setContentView(R.layout.activity_splash)

        // 四张图
        val imageList = arrayOf<Int>(R.drawable.guide01, R.drawable.guide02, R.drawable.guide03, R.drawable.guide04)

        // 设置图片滚动
        splash_guide.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        splash_guide.setIndicatorGravity(BannerConfig.CENTER)
        splash_guide.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, o: Any?, imageView: ImageView?) {
                ImgUtils.load(context, o.toString(), imageView)
            }
        })
        splash_guide.setImages(Arrays.asList(imageList))
        splash_guide.isAutoPlay(false)
        splash_guide.start()

        // 滑动监听
        splash_guide.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == imageList.size - 1) {
                    splash_enter.visibility = View.VISIBLE
                }
            }
        })

        // 进入主页
        splash_enter.setOnClickListener({
            goMainActivity()
        })
    }

    /**
     * 跳转到主页面
     */
    private fun goMainActivity() {
        // 注意, 这里并没有setContentView, 单纯只是用来跳转到相应的Activity.
        // 目的是减少首屏渲染
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
