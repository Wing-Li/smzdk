package com.lyl.smzdk.ui.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.ui.MainActivity;
import com.lyl.smzdk.view.AndroidBug5497Workaround;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: lyl
 * Date Created : 2017/12/4.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.register_skip)
    TextView registerSkip;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_btn_layout)
    LinearLayout loginBtnLayout;

    // 真正的沉浸式模式
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            AndroidBug5497Workaround.assistActivity(this);
        }

        ButterKnife.bind(this);
        setupWindowAnimations();
        initView();
    }

    private void setupWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Fade slide = new Fade();
            slide.setDuration(1300);
            getWindow().setEnterTransition(slide);
            getWindow().setExitTransition(slide);
        }
    }

    private void initView() {
    }

    @OnClick(R.id.register_skip)
    void skipRegister() {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        skipActivity(intent, false);
    }

    @OnClick(R.id.login_btn)
    void login() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
    }
}
