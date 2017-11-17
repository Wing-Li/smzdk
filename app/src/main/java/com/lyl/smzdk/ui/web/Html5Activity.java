package com.lyl.smzdk.ui.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: lyl
 * Date Created : 2017/11/17.
 */
public class Html5Activity extends BaseActivity {

    @BindView(R.id.actionbar_img_left)
    ImageView actionbarImgLeft;
    @BindView(R.id.actionbar_title)
    TextView actionbarTitle;
    @BindView(R.id.actionbar_img_right)
    ImageView actionbarImgRight;
    @BindView(R.id.actionbar_layout)
    RelativeLayout actionbarLayout;
    @BindView(R.id.web_sbr)
    SeekBar mSeekBar;
    @BindView(R.id.web_layout)
    FrameLayout mLayout;
    private String mUrl;
    private String mTitle;

    private Html5WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        getParameter();
        if (TextUtils.isEmpty(mUrl)){
            showToast(R.string.data_error);
            finish();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().setDuration(getResources().getInteger(R.integer.anim_duration_long));
        }

        initView();
    }

    private void initView() {
        // 创建 WebView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new Html5WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);
        mWebView.setWebChromeClient(new Html5WebChromeClient());
        mWebView.setWebViewClient(new Html5WebClient());
        mWebView.loadUrl(mUrl);
    }

    // 继承 WebView 里面实现的基类
    class Html5WebChromeClient extends Html5WebView.BaseWebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 顶部显示网页加载进度
            mSeekBar.setProgress(newProgress);
        }

    }

    class Html5WebClient extends Html5WebView.BaseWebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mSeekBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mSeekBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        // 销毁 WebView
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    public void getParameter() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constans.I_WEB_URL);
        mTitle = intent.getStringExtra(Constans.I_WEB_TITLE);

        actionbarTitle.setText(mTitle);
    }

    private long mOldTime;

    /**
     * 点击“返回键”，返回上一层
     * 双击“返回键”，返回到最开始进来时的网页
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mOldTime < 1500) {
                mWebView.clearHistory();
                mWebView.loadUrl(mUrl);
            } else if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                Html5Activity.this.finish();
            }
            mOldTime = System.currentTimeMillis();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
