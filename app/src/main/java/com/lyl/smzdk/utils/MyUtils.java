package com.lyl.smzdk.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.lyl.smzdk.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lyl on 2017/5/10.
 */

public class MyUtils {

    public static boolean isDev() {
        if ("dev".equals(BuildConfig.Environment)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用户是否登录
     */
    public static boolean isLogin(Context mContext) {
        return false;
    }

    public static void showT(Context context, int r) {
        Toast.makeText(context, r, Toast.LENGTH_SHORT).show();
    }

    /**
     * 随即获取指定位数的一串纯数字字符串
     *
     * @param num 几位数
     * @return
     */
    public static String getRandomNumber(int num) {
        long divisor = (long) Math.pow(10, num);

        Random random = new Random();
        long abs = Math.abs(random.nextLong() % divisor);
        String s = String.valueOf(abs);
        for (int i = 0; i < 10 - s.length(); i++) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * 检查输入的字符串
     *
     * @param str           被检查的内容
     * @param length        要求的长度
     * @param isSpecialChar 是否可以输入特殊字符
     * @return 符合要求则返回原字符串；否则，返回 空字符串
     */
    public static String checkStr(Context mContext, String str, int length, boolean isSpecialChar) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(mContext, "请输入内容", Toast.LENGTH_SHORT).show();
            return "";
        }
        if (str.length() > length) {
            Toast.makeText(mContext, "不能超过 " + length + " 个字符", Toast.LENGTH_SHORT).show();
            return "";
        }
        if (!isSpecialChar) {
            String zhengze = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";
            Pattern pattern = Pattern.compile(zhengze);
            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                Toast.makeText(mContext, "只能输入由中文、英文、数字、下划线组成的字符串", Toast.LENGTH_SHORT).show();
                return "";
            }
        }
        return str;
    }

    /**
     * 根据时间戳 返回时间 12-14 13:25
     *
     * @param cur
     * @return
     */
    public static String getDate(long cur) {
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(cur);
//        return c.get(Calendar.MONTH) + 1 + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) +
// ":" + c.get(Calendar
// .MINUTE);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(new Date(cur));
    }


    /**
     * 分享App
     */
//    public static void shareApp(Activity activity) {
//        try {
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("text/plain");
//            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.share));//主题
//            intent.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.share_txt, MyApp.mAppShare));//文本
//            activity.startActivity(intent);
//        } catch (Exception e) {
//            ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context
// .CLIPBOARD_SERVICE);
//            clipboardManager.setPrimaryClip(ClipData.newPlainText("text", activity.getString(R.string.share_txt,
//                    MyApp.mAppShare)));
//
//            Toast.makeText(activity.getApplicationContext(), R.string.share_error_hint, Toast.LENGTH_LONG).show();
//        }
//    }
}
