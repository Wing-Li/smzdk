package com.lyl.smzdk.ui.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_username)
    EditText registerUsername;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_password_again)
    EditText registerPasswordAgain;
    @BindView(R.id.register_nickname)
    EditText registerNickname;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.register_sex_male)
    RadioButton registerSexMale;
    @BindView(R.id.register_sex_femle)
    RadioButton registerSexFemle;
    @BindView(R.id.register_sex_rg)
    RadioGroup registerSexRg;
    @BindView(R.id.register_go_login)
    TextView registerGoLogin;
    @BindView(R.id.register_go_main)
    TextView registerGoMain;

    // 默认性别 为女
    private int mSex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            AndroidBug5497Workaround.assistActivity(this);
        }

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        // 选择性别的监听
        registerSexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.register_sex_male: // 男
                        mSex = 1;
                        break;

                    case R.id.register_sex_femle: //女
                        mSex = 0;
                        break;

                }
            }
        });
    }

    /**
     * 跳转到登录页面
     */
    @OnClick(R.id.register_go_login)
    void skipLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        skipActivity(intent, false);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    /**
     * 跳转到登录页面
     */
    @OnClick(R.id.register_go_main)
    void skipMainActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        skipActivity(intent, false);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    /**
     * 点击注册按钮
     */
    @OnClick(R.id.register_btn)
    void login() {
        String number = registerUsername.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        String passWordAgain = registerPasswordAgain.getText().toString().trim();
        String nickname = registerNickname.getText().toString().trim();

        // 检查 用户名、密码、昵称、性别 是否符合规范
        if (TextUtils.isEmpty(number) || number.length() > 32 || number.length() < 4) {
            t(getString(R.string.toast_username_length));
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() > 32 || password.length() < 8) {
            t(getString(R.string.toast_password_length));
            return;
        }
        if (!password.equals(passWordAgain)) {
            t(getString(R.string.toast_password_notdif));
            return;
        }
        if (TextUtils.isEmpty(nickname) || nickname.length() > 16) {
            t(getString(R.string.toast_nickname_length));
            return;
        }

        // 将数据发送给服务器
        Observable<BaseCallBack<User>> userObservable = Network.getMyApi().createUser(number, password, nickname, mSex);
        new MyApiImp<User>().request(userObservable, new MyApiImp.NetWorkCallBack<User>() {
            @Override
            public void onSuccess(User obj) {
                // 保存用户信息到配置文件
                new UserInfoModel(getApplicationContext()).save(obj);

                // 跳转到主页面，清空之前所有栈
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                t(obj.getName() + getString(R.string.toast_register_success));
            }

            @Override
            public void onFail(int code, String msg) {
                DialogUtils.showErrorDialog(mContext, msg);
            }
        });
    }

}
