package com.lyl.smzdk.ui.image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NhEassay;
import com.lyl.smzdk.view.HackyViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends BaseImageActivity {

    @BindView(R.id.image_viewPager)
    HackyViewPager imageViewPager;
    @BindView(R.id.image_btn)
    Button imageBtn;

    private ImageAdapter mImageAdapter;

    private List<NhEassay.DataBeanX.DataBean.GroupBean.LargeImageListBean> mImageListBeanList;
    private int mPostion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        getParameter();

        initView();
    }

    private void getParameter() {
        Intent intent = getIntent();
        mPostion = intent.getIntExtra(Constans.IMAGE_LIST_POSTION, 0);
        mImageListBeanList = (List<NhEassay.DataBeanX.DataBean.GroupBean.LargeImageListBean>) intent
                .getSerializableExtra(Constans.IMAGE_LIST);
    }

    private void initView() {
        mImageAdapter = new ImageAdapter(mContext, mImageListBeanList);
        imageViewPager.setAdapter(mImageAdapter);
        imageViewPager.setOffscreenPageLimit(2);
        imageViewPager.setCurrentItem(mPostion);

        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPostion = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download(imageBtn, mImageListBeanList.get(mPostion).getUrl());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
