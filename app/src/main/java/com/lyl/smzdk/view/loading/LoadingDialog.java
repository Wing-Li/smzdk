package com.lyl.smzdk.view.loading;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.lyl.smzdk.R;

/**
 * Author: lyl
 * Date Created : 2018/1/12.
 */
public class LoadingDialog extends Dialog {

    // 动画播放的时间
    private int AnimTime = 1500;
    private LVGhost lvGhost;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.Theme_CustomProgressDialog);
        setContentView(R.layout.dialog_loading_zombie);
        lvGhost = findViewById(R.id.lv_ghost);
    }

    @Override
    public void show() {
        super.show();
        lvGhost.startAnim(AnimTime);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        lvGhost.stopAnim();
    }

    @Override
    public void cancel() {
        super.cancel();
        lvGhost.stopAnim();
    }
}
