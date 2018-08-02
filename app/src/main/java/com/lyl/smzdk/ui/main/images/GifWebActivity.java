package com.lyl.smzdk.ui.main.images;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.ui.image.BaseImageActivity;
import com.lyl.smzdk.utils.StatusBarCompat;
import com.lyl.smzdk.view.Html5WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: lyl
 * Date Created : 2018/2/11.
 */

public class GifWebActivity extends BaseImageActivity {

    @BindView(R.id.web_sbr)
    SeekBar mSeekBar;
    @BindView(R.id.web_layout)
    FrameLayout mLayout;
    @BindView(R.id.web_back)
    View webBack;

    private Html5WebView mWebView;

    private String mUrl;
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_gif);
        ButterKnife.bind(this);

        translucentNavigation();

        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constans.I_URL);
        mTitle = intent.getStringExtra(Constans.I_TITLE);

        if (TextUtils.isEmpty(mUrl)) {
            showToast(R.string.data_error);
            finish();
            return;
        }

        initView();
    }

    private void initView() {
        // 创建 WebView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .MATCH_PARENT);
        params.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext), 0, 0);
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
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("cdn.sogou.com/")) {
                download(null, url, true);
                return false;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

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

    @OnClick(R.id.web_back)
    public void back() {
        onBackPressed();
    }

    // ===================================================================================================

    private long mOldTime;

    /**
     * 点击“返回键”，返回上一层
     * 双击“返回键”，返回到最开始进来时的网页
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mOldTime < 1500) {
            mWebView.clearHistory();
            mWebView.loadUrl(mUrl);
            return;
        } else if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        } else {
            finish();
        }
        mOldTime = System.currentTimeMillis();
    }
}
