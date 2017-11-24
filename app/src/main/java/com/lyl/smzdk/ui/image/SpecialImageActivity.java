package com.lyl.smzdk.ui.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.PhotoView;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.LogUtils;

import java.security.MessageDigest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialImageActivity extends BaseImageActivity {


    @BindView(R.id.long_image)
    SubsamplingScaleImageView longImage;
    @BindView(R.id.gif_image)
    PhotoView gifImage;
    @BindView(R.id.image_btn)
    Button imageBtn;

    private String mUrl;
    private String mType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_special);
        ButterKnife.bind(this);

        getPrameter();
        initView();
    }

    private void initView() {
        if (Constans.SPECIAL_IMAGE_GIF.equals(mType)) {
            longImage.setVisibility(View.GONE);
            gifImage.setVisibility(View.VISIBLE);

            ImgUtils.load(mContext, mUrl, gifImage);
        }else if (Constans.SPECIAL_IMAGE_LONG.equals(mType)) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            longImage.setScaleAndCenter(1f, new PointF(displayMetrics.widthPixels, displayMetrics.widthPixels));
            longImage.setVisibility(View.VISIBLE);
            gifImage.setVisibility(View.GONE);


            ImgUtils.getBitmap(mContext, mUrl, new BitmapTransformation() {
                @Override
                public void updateDiskCacheKey(MessageDigest messageDigest) {
                    try {
                        messageDigest.update((mContext.getPackageName() + "BitmapTransform").getBytes("utf-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                    longImage.setImage(ImageSource.bitmap(toTransform));
                    return toTransform;
                }
            });

        }else {
            // 默认使用正常的图
            // ConstantIntent.SPECIAL_IMAGE_NORMAL
            longImage.setVisibility(View.GONE);
            gifImage.setVisibility(View.VISIBLE);

            ImgUtils.load(mContext, mUrl, gifImage);
        }

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mType.equals(Constans.SPECIAL_IMAGE_GIF)) {
                    download(imageBtn, mUrl, true);
                } else {
                    download(imageBtn, mUrl, false);
                }
            }
        });
    }

    private void getPrameter() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constans.SPECIAL_IMAGE_URL);
        mType = intent.getStringExtra(Constans.SPECIAL_IMAGE_TYPE);
        LogUtils.d("显示单张图片：", mType + " : " + mUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUrl = null;
        longImage = null;
        gifImage.setImageBitmap(null);
        gifImage = null;
    }
}