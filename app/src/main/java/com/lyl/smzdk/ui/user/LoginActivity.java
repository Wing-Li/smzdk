package com.lyl.smzdk.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
    }

    @OnClick(R.id.register_skip)
    void skipRegister(){
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
