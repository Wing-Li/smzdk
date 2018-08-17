package com.lyl.smzdk.ui.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.dao.model.UserInfoModel;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.myapi.BaseCallBack;
import com.lyl.smzdk.network.entity.myapi.User;
import com.lyl.smzdk.network.imp.MyApiImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.ui.MainActivity;
import com.lyl.smzdk.utils.DialogUtils;
import com.lyl.smzdk.view.AndroidBug5497Workaround;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            AndroidBug5497Workaround.assistActivity(this);
        }

        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_skip)
    void skipRegister() {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        skipActivity(intent, false);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    @OnClick(R.id.login_btn)
    void login() {
        String number = loginUsername.getText().toString().trim();
        String pwd = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(pwd)) {
            t(getString(R.string.toast_number_pwd_not_empty));
            return;
        }

        Observable<BaseCallBack<User>> login = Network.getMyApi().login(number, pwd);
        new MyApiImp<User>().request(login, new MyApiImp.NetWorkCallBack<User>() {
            @Override
            public void onSuccess(User obj) {
                // 保存用户信息到配置文件
                new UserInfoModel(getApplicationContext()).save(obj);

                // 跳转到主页面，清空之前所有栈
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                t(obj.getName() + getString(R.string.toast_login_success));
            }

            @Override
            public void onFail(int code, String msg) {
                DialogUtils.showErrorDialog(mContext, msg);
            }
        });
    }
}
