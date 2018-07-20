package com.lyl.smzdk.ui.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.PhotoView;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.LogUtils;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_special);
        ButterKnife.bind(this);

        getParameter();
        initView();
    }

    private void initView() {
        if (Constans.SPECIAL_IMAGE_LONG.equals(mType)) {

            longImage.setVisibility(View.VISIBLE);
            gifImage.setVisibility(View.GONE);

            ImgUtils.getBitmap(mContext, mUrl, new ImgUtils.BitmapCallback() {
                @Override
                public void onBitmap(final Bitmap bitmap) {
                    if (bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                longImage.setImage(ImageSource.bitmap(bitmap));
                            }
                        });
                    }
                }
            });

        } else {
            // 默认使用正常的图
            longImage.setVisibility(View.GONE);
            gifImage.setVisibility(View.VISIBLE);

            ImgUtils.load(mContext, mUrl, gifImage);
        }

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download(imageBtn, mUrl, false);
            }
        });
    }

    private void getParameter() {
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
