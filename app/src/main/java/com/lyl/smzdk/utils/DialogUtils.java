package com.lyl.smzdk.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.lyl.smzdk.R;

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
}
