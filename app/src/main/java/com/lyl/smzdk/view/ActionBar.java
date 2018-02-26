package com.lyl.smzdk.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyl.smzdk.R;


/**
 * Created by lyl on 2017/7/28.
 */

public class ActionBar extends RelativeLayout {

    private Context context;

    public View mStatusBar;
    public RelativeLayout mLayout;
    public RelativeLayout mLeftLayout;
    public ImageView mImgLeft;
    public TextView mTxtLet;
    public RelativeLayout mRightLayout;
    public TextView mTitle;
    public ImageView mImgRight;
    public TextView mTxtRight;


    public ActionBar(Context context) {
        super(context);
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        context = getContext();
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_actionbar, this);

        mStatusBar = view.findViewById(R.id.actionbar_statusbar);
        mLayout = view.findViewById(R.id.actionbar_layout);
        mLeftLayout = view.findViewById(R.id.actionbar_left_layout);
        mImgLeft = view.findViewById(R.id.actionbar_img_left);
        mTxtLet = view.findViewById(R.id.actionbar_txt_left);
        mTitle = view.findViewById(R.id.actionbar_title);
        mRightLayout = view.findViewById(R.id.actionbar_right_layout);
        mImgRight = view.findViewById(R.id.actionbar_img_right);
        mTxtRight = view.findViewById(R.id.actionbar_txt_right);
    }

    private void setTitle(int resTitle) {
        mTitle.setText(resTitle);
    }

    private void setTitle(String resTitle) {
        mTitle.setText(resTitle);
    }

    public void setTitleColor(int resColor) {
        mTitle.setTextColor(ContextCompat.getColor(context, resColor));
    }

    private void setBack(final Activity activity) {
        mLeftLayout.setVisibility(View.VISIBLE);
        mImgLeft.setVisibility(View.VISIBLE);
        mLeftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    /**
     * 只有标题
     */
    public void setModelOnlyTitle(int resTitle) {
        setTitle(resTitle);
    }

    public void setModelOnlyTitle(String resTitle) {
        setTitle(resTitle);
    }

    /**
     * 标题、返回按钮（默认关闭本页面）
     *
     * @param resTitle 标题
     * @param activity 返回按钮关闭当前页面
     */
    public void setModelBack(int resTitle, final Activity activity) {
        setTitle(resTitle);
        setBack(activity);
    }

    public void setModelBack(String strTitle, final Activity activity) {
        setTitle(strTitle);
        setBack(activity);
    }

    /**
     * 标题、返回按钮（自定义事件）
     *
     * @param resTitle        标题
     * @param onClickListener 自定义事件
     */
    public void setModelLeft(int resTitle, final OnClickListener onClickListener) {
        setTitle(resTitle);
        mLeftLayout.setVisibility(View.VISIBLE);
        mImgLeft.setVisibility(View.VISIBLE);
        mLeftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        });

        mRightLayout.setVisibility(View.GONE);
        mRightLayout.setOnClickListener(null);
    }

    public void setModelLeft(String resTitle, final OnClickListener onClickListener) {
        setTitle(resTitle);
        mLeftLayout.setVisibility(View.VISIBLE);
        mImgLeft.setVisibility(View.VISIBLE);
        mLeftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        });

        mRightLayout.setVisibility(View.GONE);
        mRightLayout.setOnClickListener(null);
    }

    /**
     * 标题、确定按钮
     *
     * @param resTitle        标题
     * @param onClickListener 确定按钮点击事件
     */
    public void setModelRight(int resTitle, final OnClickListener onClickListener) {
        setTitle(resTitle);
        mLeftLayout.setVisibility(View.GONE);
        mImgLeft.setVisibility(View.GONE);
        mLeftLayout.setOnClickListener(null);

        mTxtRight.setText(R.string.ok);
        mTxtRight.setVisibility(View.VISIBLE);
        mRightLayout.setVisibility(View.VISIBLE);
        mRightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        });
    }

    /**
     * 左边返回、右边自定文字（自定义点击事件）
     *
     * @param activity        本页面
     * @param resTitle        中间标题
     * @param resRight        右边文字
     * @param onClickListener 右边文字的点击事件
     */
    public void setModelLeftAndRightTxt(Activity activity, int resTitle, int resRight, final OnClickListener
            onClickListener) {
        setTitle(resTitle);
        setBack(activity);

        mTxtRight.setText(resRight);
        mRightLayout.setVisibility(View.VISIBLE);
        mTxtRight.setVisibility(View.VISIBLE);
        mRightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        });
    }

    /**
     * 左边返回、右边自定文字（自定义点击事件）
     *
     * @param activity        本页面
     * @param resTitle        中间标题
     * @param resImgRight     右边文字
     * @param onClickListener 右边文字的点击事件
     */
    public void setModelLeftAndRightImg(Activity activity, int resTitle, int resImgRight, final OnClickListener
            onClickListener) {
        setTitle(resTitle);
        setBack(activity);

        mImgRight.setImageResource(resImgRight);
        mRightLayout.setVisibility(View.VISIBLE);
        mImgRight.setVisibility(View.VISIBLE);
        mRightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        });
    }

    public void setStatusBarColor(int resColor){
        mStatusBar.setBackgroundResource(resColor);
    }

    public void setOkHide() {
        mTxtRight.setVisibility(View.GONE);
    }

    public boolean isOkShow() {
        return mTxtRight.isShown();
    }

    public RelativeLayout getmLeftLayout() {
        return mLeftLayout;
    }

    public ImageView getmImgLeft() {
        return mImgLeft;
    }

    public TextView getmTxtLet() {
        return mTxtLet;
    }

    public TextView getmTitle() {
        return mTitle;
    }

    public RelativeLayout getmRightLayout() {
        return mRightLayout;
    }

    public ImageView getmImgRight() {
        return mImgRight;
    }

    public TextView getmTxtRight() {
        return mTxtRight;
    }
}
