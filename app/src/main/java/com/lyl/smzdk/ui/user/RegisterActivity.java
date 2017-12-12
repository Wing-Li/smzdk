package com.lyl.smzdk.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_username)
    EditText registerUsername;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_password_again)
    EditText registerPasswordAgain;
    @BindView(R.id.register_password_again_layout)
    LinearLayout registerPasswordAgainLayout;
    @BindView(R.id.register_nickname)
    EditText registerNickname;
    @BindView(R.id.register_nickname_layout)
    LinearLayout registerNicknameLayout;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.register_btn_layout)
    LinearLayout registerBtnLayout;
    @BindView(R.id.register_back)
    TextView registerBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Fade slide = new Fade();
            slide.setDuration(1300);

            getWindow().setEnterTransition(slide);
            getWindow().setExitTransition(slide);
        }
    }

    @OnClick(R.id.register_back)
    void skipLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        skipActivity(intent, false);
    }

    @OnClick(R.id.register_btn)
    void login() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
    }

}
