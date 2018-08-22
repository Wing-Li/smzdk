package com.lyl.smzdk.ui.user;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyl.smzdk.MyApp;
import com.lyl.smzdk.R;
import com.lyl.smzdk.dao.model.UserInfoModel;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.MyUtils;
import com.lyl.smzdk.view.imageview.MyImageView;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.OnClick;

public class UserFragment extends BaseFragment {


    @BindView(R.id.user_login)
    LinearLayout userLogin;
    @BindView(R.id.user_software)
    LinearLayout userSoftware;
    @BindView(R.id.user_announce)
    LinearLayout userAnnounce;
    @BindView(R.id.user_qq_group)
    LinearLayout userQqGroup;
    @BindView(R.id.user_share)
    LinearLayout userShare;
    @BindView(R.id.user_suggestion)
    LinearLayout userSuggestion;
    @BindView(R.id.user_update)
    LinearLayout userUpdate;
    @BindView(R.id.user_about)
    LinearLayout userAbout;

    @BindView(R.id.user_info_icon)
    MyImageView userInfoIcon;
    @BindView(R.id.user_info_name)
    TextView userInfoName;
    @BindView(R.id.user_info_sex)
    ImageView userInfoSex;
    @BindView(R.id.user_info_integral)
    ImageView userInfoIntegral;
    @BindView(R.id.user_info_openvip)
    Button userInfoOpenvip;
    @BindView(R.id.user_info_layout)
    RelativeLayout userInfoLayout;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onHiddenChanged(false);
        mActionBar.setModelOnlyTitle(R.string.user_title);
    }

    @Override
    public void onStart() {
        super.onStart();

        initView();
    }

    /**
     * 初始化布局参数
     */
    private void initView() {
        if (MyUtils.isLogin(getHolder())) {
            // 用户已经登录
            UserInfoModel user = new UserInfoModel(getHolder());

            userLogin.setVisibility(View.GONE);
            userInfoLayout.setVisibility(View.VISIBLE);

            userInfoName.setText(user.getName());
            // 头像如果是空，根据性别显示不同的默认头像
            if (TextUtils.isEmpty(user.getIcon())){
                if (user.getSex() == 0) {
                    userInfoIcon.setImageResource(R.drawable.ic_sex_girl_default);
                } else {
                    userInfoIcon.setImageResource(R.drawable.ic_sex_boy_default);
                }
            } else {
                ImgUtils.loadRound(getHolder(), user.getIcon(), userInfoIcon);
            }
            // 根据性别设置性别图标
            if (user.getSex() == 0) {
                userInfoSex.setImageResource(R.drawable.ic_sex_girl);
            } else {
                userInfoSex.setImageResource(R.drawable.ic_sex_boy);
            }

        } else {
            // 用户没有登录，隐藏用户信息布局
            userLogin.setVisibility(View.VISIBLE);
            userInfoLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 此 Fragemnt 用户可见
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            setStatusBarColor(R.color.user_primary);
        }
    }

    /**
     * 跳转到登录页面
     */
    @OnClick(R.id.user_login)
    void skipLogin() {
        Intent intent = new Intent(getHolder(), LoginActivity.class);
        skipActivity(intent, false);
        getHolder().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    /**
     * 跳转到 用户信息 页面
     */
    @OnClick(R.id.user_info_layout)
    void skipUserInfo() {
        startActivity(new Intent(getHolder(), UserInfoActivity.class));
    }

    /**
     * 跳转到 开通会员页面
     */
    @OnClick(R.id.user_info_openvip)
    void skipOpenVip() {
        startActivity(new Intent(getHolder(), RechargeVipActivity.class));
    }

    /**
     * 加入 QQ 群
     */
    @OnClick(R.id.user_qq_group)
    void joinQQGroup() {
        // 发起添加群流程。群号：什么值得看(530224801)
        Intent intent = new Intent();
        intent.setData(Uri.parse(getString(R.string.join_qq)));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
        } catch (Exception e) {
            showToast(getString(R.string.join_qq_fail));
        }
    }

    /**
     * 分享 App
     */
    @OnClick(R.id.user_share)
    void shareApp() {
        String shareText = getString(R.string.share_text, MyApp.appDownloadUrl);
        try {
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(textIntent, "share"));
        } catch (Exception e) {
            ClipboardManager clipboardManager = (ClipboardManager) getHolder().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("text", shareText));
            showToast(getString(R.string.share_app_fail));
        }
    }

    /**
     * 跳转到建议反馈
     */
    @OnClick(R.id.user_suggestion)
    void feedback() {
        startActivity(new Intent(getHolder(), FeedbackActivity.class));
    }

    /**
     * 手动更新 App
     */
    @OnClick(R.id.user_update)
    void updateApp() {
        /**
         * @param isManual  用户手动点击检查，非用户点击操作请传false
         * @param isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
         */
        Beta.checkUpgrade(true, false);
    }

    /**
     * 跳转到 关于 页面
     */
    @OnClick(R.id.user_about)
    void aboutMe() {
        startActivity(new Intent(getHolder(), AboutActivity.class));
    }
}
