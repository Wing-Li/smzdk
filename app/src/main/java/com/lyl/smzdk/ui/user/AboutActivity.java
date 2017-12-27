package com.lyl.smzdk.ui.user;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.PlayUtils;
import com.lyl.smzdk.view.ActionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.about_version)
    TextView aboutVersion;
    @BindView(R.id.about_zhichi)
    TextView aboutZhichi;
    @BindView(R.id.about_join_qq)
    TextView aboutJoinQq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        actionbar.setBackgroundResource(R.color.colorPrimaryDark);
        actionbar.setModelBack(R.string.acout, mActivity);

        String versionName = "1.0.0";
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        aboutVersion.setText("Version : " + versionName);
    }

    @OnClick(R.id.about_zhichi)
    void ZhiChi() {
        PlayUtils.play(mContext);
    }

    @OnClick(R.id.about_join_qq)
    void joinQQ() {
        Intent intent = new Intent();
        intent.setData(Uri.parse(getString(R.string.join_qq)));
        try {
            startActivity(intent);
        } catch (Exception e) {
            showToast(getString(R.string.join_qq_fail));
        }
    }
}
