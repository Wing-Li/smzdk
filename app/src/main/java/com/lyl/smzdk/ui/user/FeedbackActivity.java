package com.lyl.smzdk.ui.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lyl.smzdk.R;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.myapi.BaseCallBack;
import com.lyl.smzdk.network.imp.MyApiImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.DialogUtils;
import com.lyl.smzdk.view.ActionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.feedback_title)
    EditText feedbackTitle;
    @BindView(R.id.feedback_edt)
    EditText feedbackEdt;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        actionbar.setModelBack(R.string.feedback, mActivity);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = feedbackTitle.getText().toString().trim();
                String content = feedbackEdt.getText().toString().trim();

                // 标题或内容为空
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                    DialogUtils.showInfoDialog(mContext, getString(R.string.dialog_input_empty));
                    return;
                }

                sendFeedback(title, content);
            }
        });
    }

    /**
     * 提交前的提示框
     */
    private void sendFeedback(final String title, final String content) {
        new AlertDialog.Builder(this)//
                .setTitle(R.string.dialog_title_hint)//
                .setMessage(R.string.dialog_content_put_ask)//
                .setNegativeButton(R.string.cancel, null)//
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        requestFeedback(title, content);

                    }
                }).create().show();
    }

    /**
     * 提交建议
     */
    private void requestFeedback(String title, String content) {
        Observable<BaseCallBack<String>> addFeedBack = Network.getMyApi().addFeedBack(mUserInfoModel.getId(), mUserInfoModel.getName(), title,
                content);

        new MyApiImp<String>().request(addFeedBack, new MyApiImp.NetWorkCallBack<String>() {
            @Override
            public void onSuccess(String obj) {
                // 提交成功，关闭页面
                t(getString(R.string.toast_feedback_success));
                finish();
            }

            @Override
            public void onFail(int code, String msg) {
                DialogUtils.showErrorDialog(mContext, msg);
            }
        });
    }
}
