package com.lyl.smzdk.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.user.RechargeVipActivity;
import com.lyl.smzdk.ui.user.RegisterActivity;

/**
 * Author: lyl
 * Date Created : 2018/8/21.
 */
public class SnackbarUtils {

    /**
     * api 权限不够 提示
     */
    public static void showRechargeVipDialog(final Context context, View view) {
        final String content = MyUtils.isLogin(context) ? context.getString(R.string.dialog_recharge_vip) : context.getString(R.string.dialog_register_user);

        Snackbar.make(view, content, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent;
                        // 已经登陆了就去充值会员，没登陆 就去注册
                        if (MyUtils.isLogin(context)) {
                            intent = new Intent(context, RechargeVipActivity.class);
                        } else {
                            intent = new Intent(context, RegisterActivity.class);
                        }
                        context.startActivity(intent);

                    }
                }).show();
    }
}
