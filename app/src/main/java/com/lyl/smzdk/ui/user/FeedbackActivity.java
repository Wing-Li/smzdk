package com.lyl.smzdk.ui.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.view.ActionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                sendFeedback();
            }
        });
    }

    private void sendFeedback() {
        new AlertDialog.Builder(this)//
                .setTitle(R.string.dialog_title_hint)//
                .setMessage(R.string.dialog_content_put_ask)//
                .setNegativeButton(R.string.cancel, null)//
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = feedbackTitle.getText().toString().trim();
                        String content = feedbackEdt.getText().toString().trim();
                    }
                }).create().show();
    }
}
