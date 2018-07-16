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
import com.lyl.smzdk.dao.imp.HistoryImp;
import com.lyl.smzdk.network.imp.news.DzImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.LogUtils;
import com.lyl.smzdk.view.Html5WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private String mType;

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

        // 将这个网页存进历史记录
        new HistoryImp(getApplicationContext()).saveHistory(mTitle, mUrl);

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

        if (TextUtils.isEmpty(mType)) {
            mWebView.loadUrl(mUrl);
        } else {
            switch (mType) {
                case Constans.NEWS_TYPE_DUZHE:
                case Constans.NEWS_TYPE_XIUXIAN:
                    replaceHtml();
                    break;
                default:
                    mWebView.loadUrl(mUrl);
                    break;
            }
        }
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
        mUrl = intent.getStringExtra(Constans.I_URL);
        mTitle = intent.getStringExtra(Constans.I_TITLE);
        mType = intent.getStringExtra(Constans.I_CHANNEL_TYPE_TYPE);
        LogUtils.d("WebView 打开链接：" + mUrl);

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


    private void replaceHtml() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> ob) throws Exception {
                String html = "";
                switch (mType) {
                    case Constans.NEWS_TYPE_DUZHE: {
                        DzImp dzImp = new DzImp();
                        String detail = dzImp.getDetail(mUrl);
                        html = HTML_MODEL.DUZHE.replace(HTML_MODEL.REPLACE_DEFAULT, detail);
                        break;
                    }
                }

                ob.onNext(html);
            }
        })//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(String s) {
                        mWebView.loadDataWithBaseURL(null, s, "text/html", "utf-8", null);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    // ===================================================================================================

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
