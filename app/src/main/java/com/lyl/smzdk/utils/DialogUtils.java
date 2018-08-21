package com.lyl.smzdk.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.user.RechargeVipActivity;
import com.lyl.smzdk.ui.user.RegisterActivity;

/**
 * Author: lyl
 * Date Created : 2018/8/9.
 */
public class DialogUtils {

    /**
     * 弹错误对话框
     */
    public static void showErrorDialog(Context context, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)//
                .setTitle(R.string.dialog_error) //
                .setMessage(content) //
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })//
                ;

        builder.create().show();
    }

    /**
     * 提示对话框
     */
    public static void showInfoDialog(Context context, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)//
                .setTitle(R.string.dialog_title_hint) //
                .setMessage(content) //
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })//
                ;

        builder.create().show();
    }

    /**
     * api 权限不够对话框
     */
    public static void showRechargeVipDialog(final Context context) {
        final String content = MyUtils.isLogin(context) ? context.getString(R.string.dialog_recharge_vip) : context.getString(R.string.dialog_register_user);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)//
                .setTitle(R.string.dialog_title_hint) //
                .setMessage(content) //
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        // 已经登陆了就去充值会员，没登陆 就去注册
                        if (MyUtils.isLogin(context)) {
                            intent = new Intent(context, RechargeVipActivity.class);
                        } else {
                            intent = new Intent(context, RegisterActivity.class);
                        }
                        context.startActivity(intent);

                        dialog.dismiss();
                    }
                })//
                ;

        builder.create().show();
    }

}
