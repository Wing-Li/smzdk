package com.lyl.smzdk.ui.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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

    @BindView(R.id.web_actionbar_img_left)
    ImageView actionbarImgLeft;
    @BindView(R.id.web_actionbar_title)
    TextSwitcher actionbarTitle;
    @BindView(R.id.web_actionbar_img_right)
    ImageView actionbarImgRight;
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
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        getParameter();
        if (TextUtils.isEmpty(mUrl)) {
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
        actionbarImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

        setTitleAnims();
        actionbarTitle.setText(mTitle);
    }

    private void setTitleAnims() {
        // 注意：必须在 setText() 之前
        actionbarTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView textView = new TextView(mContext);
                textView.setSingleLine(true);
                textView.setTextAppearance(mContext, R.style.action_title_style);
                textView.setGravity(Gravity.CENTER);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSelected(true);
                    }
                }, 1738);
                return textView;
            }
        });
        actionbarTitle.setInAnimation(this, android.R.anim.fade_in);
        actionbarTitle.setOutAnimation(this, android.R.anim.fade_out);
    }

//    /**
//     * 如果传过来的不是完整的Html，而是只有body部分的内容，那么我们就需要补充并添加一些css样式来达到自适应的效果。
//     */
//    private void loadW(){
//        webView.loadData(getHtmlData(body), "text/html; charset=utf-8", "utf-8");
//    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, " +
                "user-scalable=no\"> " + "<style>img{max-width: 100%; width:auto; height:auto;}</style>" + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
            mOldTime = System.currentTimeMillis();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
