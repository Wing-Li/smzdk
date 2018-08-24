package com.lyl.smzdk.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View

import com.lyl.smzdk.R
import com.lyl.smzdk.ui.user.RechargeVipActivity
import com.lyl.smzdk.ui.user.RegisterActivity

/**
 * Author: lyl
 * Date Created : 2018/8/21.
 */
object SnackbarUtils {

    private lateinit var mSnackbar: Snackbar

    /**
     * api 权限不够 提示
     */
    fun showRechargeVipDialog(context: Context, view: View) {
        val content = if (MyUtils.isLogin(context))
            context.getString(R.string.dialog_recharge_vip)
        else
            context.getString(R.string
                    .dialog_register_user)

        if (!::mSnackbar.isInitialized) {
            mSnackbar = Snackbar.make(view, content, Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE).setAction(R.string.ok) {
                val intent: Intent
                // 已经登陆了就去充值会员，没登陆 就去注册
                if (MyUtils.isLogin(context)) {
                    intent = Intent(context, RechargeVipActivity::class.java)
                } else {
                    intent = Intent(context, RegisterActivity::class.java)
                }
                context.startActivity(intent)
            }
        }

        if (!mSnackbar.isShownOrQueued) {
            mSnackbar.show()
        }
    }
}
