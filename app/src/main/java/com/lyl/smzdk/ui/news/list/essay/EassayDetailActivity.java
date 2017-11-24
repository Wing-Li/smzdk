package com.lyl.smzdk.ui.news.list.essay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.NhComments;
import com.lyl.smzdk.network.entity.NhEassay;
import com.lyl.smzdk.network.imp.news.NhImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.ui.image.ImageActivity;
import com.lyl.smzdk.ui.image.SpecialImageActivity;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.MyUtils;
import com.lyl.smzdk.view.ActionBar;
import com.lyl.smzdk.view.WrappingLinearLayoutManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EassayDetailActivity extends BaseActivity {

    @BindView(R.id.actionbar)
    ActionBar mActionBar;
    @BindView(R.id.eassay_detail_icon)
    ImageView eassayDetailIcon;
    @BindView(R.id.eassay_detail_name)
    TextView eassayDetailName;
    @BindView(R.id.eassay_detail_time)
    TextView eassayDetailTime;
    @BindView(R.id.eassay_detail_content)
    TextView eassayDetailContent;

    @BindView(R.id.eassay_detail_image_content)
    ImageView eassayDetailImageContent;
    @BindView(R.id.eassay_detail_long_image_text)
    TextView eassayDetailLongImageText;
    @BindView(R.id.eassay_detail_image_content_layout)
    RelativeLayout eassayDetailImageContentLayout;
    @BindView(R.id.eassay_detail_image_grid)
    GridView eassayDetailImageGrid;

    @BindView(R.id.eassay_detail_hot_count_layout)
    LinearLayout eassayDetailHotCountLayout;
    @BindView(R.id.eassay_detail_hot_count)
    TextView eassayDetailHotCount;
    @BindView(R.id.eassay_detail_recycler_hot)
    RecyclerView eassayDetailRecyclerHot;
    @BindView(R.id.eassay_detail_all_count)
    TextView eassayDetailAllCount;
    @BindView(R.id.eassay_detail_recycler_all)
    RecyclerView eassayDetailRecyclerAll;
    @BindView(R.id.eassay_detail_layout)
    LinearLayout eassayDetailLayout;
    @BindView(R.id.eassay_detail_nestedscrollview)
    NestedScrollView eassayDetailNestedscrollview;
    @BindView(R.id.eassay_detail_hot_comment_layout)
    LinearLayout eassayDetailHotCommentLayout;
    @BindView(R.id.eassay_detail_all_comment_layout)
    LinearLayout eassayDetailAllCommentLayout;


    private String mGroupId;
    private String mUserName;
    private String mUserIcon;
    private String mTime;
    private String mContent;
    private String mCommentAllNum;
    private String mCommentHotNum;

    private int mContentType;
    private String mImageUrl;
    private List<NhEassay.DataBeanX.DataBean.GroupBean.ThumbImageListBean> mImageThumbListBeanList;
    private List<NhEassay.DataBeanX.DataBean.GroupBean.LargeImageListBean> mImageListBeanList;

    private EassayDetailCommentAdapter mHotCommentAdapter;
    private EassayDetailCommentAdapter mAllCommentAdapter;

    private List<NhComments.DataBean.CommentsBean> mHotCommentsList;
    private List<NhComments.DataBean.CommentsBean> mAllCommentsList;
    private NhImp mNeihanImp;

    private int page = 0;
    private boolean hasMore;
    private int mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eassay_detail);
        ButterKnife.bind(this);

        getParameter();
        initView();
        initData();
    }

    private void initData() {
        mNeihanImp = new NhImp(this);
        mHotCommentsList = new ArrayList<>();
        mAllCommentsList = new ArrayList<>();

        hasMore = true;
        page = 0;
        getData();
    }

    private void getData() {
        if (!hasMore) {
            showToast(R.string.not_more);
            return;
        }

        Call<NhComments> nhComments = mNeihanImp.getNhComments(mGroupId, page);
        Call<NhComments> clone = nhComments.clone();
        clone.enqueue(new Callback<NhComments>() {
            @Override
            public void onResponse(Call<NhComments> call, Response<NhComments> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NhComments body = response.body();
                    if ("success".equals(body.getMessage())) {
                        page++;
                        hasMore = body.isHas_more();

                        List<NhComments.DataBean.CommentsBean> top_comments = body.getData().getTop_comments();
                        if (top_comments != null && top_comments.size() > 0) {
                            mHotCommentsList.addAll(top_comments);
                            mHotCommentAdapter.setData(mHotCommentsList);

                            mCommentHotNum = String.valueOf(top_comments.size());
                            eassayDetailHotCount.setText("(" + mCommentHotNum + ")");
                        }
                        if (page == 1 && top_comments.size() == 0) {
                            eassayDetailHotCountLayout.setVisibility(View.GONE);
                        }

                        List<NhComments.DataBean.CommentsBean> recent_comments = body.getData().getRecent_comments();
                        if (recent_comments != null && recent_comments.size() > 0) {
                            mAllCommentsList.addAll(recent_comments);
                            mAllCommentAdapter.setData(mAllCommentsList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NhComments> call, Throwable t) {
                showToast(R.string.net_error);
                if (t != null) {
                    CrashReport.postCatchedException(t);
                }
            }
        });
    }

    private void getParameter() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            showToast(getString(R.string.parameter_error));
            finish();
            return;
        }

        mGroupId = bundle.getString(Constans.EASSAY_DETAIL_MGROUP_ID);
        mUserName = bundle.getString(Constans.EASSAY_DETAIL_NAME);
        mUserIcon = bundle.getString(Constans.EASSAY_DETAIL_ICON);
        mTime = bundle.getString(Constans.EASSAY_DETAIL_TIME);
        mContent = bundle.getString(Constans.EASSAY_DETAIL_CONTENT);
        mCommentAllNum = bundle.getString(Constans.EASSAY_DETAIL_COMMENT_ALL_NUM);

        mContentType = bundle.getInt(Constans.EASSAY_DETAIL_CONTENT_TYPE);
        switch (mContentType) {
            case Constans.EASSAY_DETAIL_CONTENT_GIF://GIF
            case Constans.EASSAY_DETAIL_CONTENT_LONG://长图
            case Constans.EASSAY_DETAIL_CONTENT_NORMAL://普通图
                mImageUrl = bundle.getString(Constans.SPECIAL_IMAGE_URL);
                break;
            case Constans.EASSAY_DETAIL_CONTENT_LIST://列表
                mImageThumbListBeanList = (List<NhEassay.DataBeanX.DataBean.GroupBean.ThumbImageListBean>) bundle
                        .getSerializable(Constans.IMAGE_THUMB_LIST);
                mImageListBeanList = (List<NhEassay.DataBeanX.DataBean.GroupBean.LargeImageListBean>) bundle
                        .getSerializable(Constans.IMAGE_LIST);
                break;
        }
    }

    private void initView() {
        mActionBar.setTitleColor(R.color.black);
        mActionBar.setModelBack(R.string.details, mActivity);

        ImgUtils.loadCircle(mContext, mUserIcon, eassayDetailIcon);

        eassayDetailName.setText(FS(mUserName));

        eassayDetailTime.setText(MyUtils.getDate(Long.parseLong(mTime)));

        eassayDetailContent.setText(FS(mContent));

        eassayDetailHotCount.setText(FS(mCommentHotNum));

        eassayDetailAllCount.setText("(" + FS(mCommentAllNum) + ")");

        // 设置图片的显示
        setImageShow();

        // 设置热门评论列表
        WrappingLinearLayoutManager wrappingLinearLayoutManager = new WrappingLinearLayoutManager(mContext);
        wrappingLinearLayoutManager.setAutoMeasureEnabled(false);
        eassayDetailRecyclerHot.setLayoutManager(wrappingLinearLayoutManager);
        mHotCommentAdapter = new EassayDetailCommentAdapter(mContext, mHotCommentsList, EassayDetailCommentAdapter
                .COMMENT_TYPE_HOT);
        eassayDetailRecyclerHot.setAdapter(mHotCommentAdapter);
        eassayDetailRecyclerHot.setNestedScrollingEnabled(false);

        // 设置全部评论列表
        WrappingLinearLayoutManager wrappingLinearLayoutManager2 = new WrappingLinearLayoutManager(mContext);
        wrappingLinearLayoutManager2.setAutoMeasureEnabled(false);
        eassayDetailRecyclerAll.setLayoutManager(wrappingLinearLayoutManager2);
        mAllCommentAdapter = new EassayDetailCommentAdapter(mContext, mAllCommentsList, EassayDetailCommentAdapter
                .COMMENT_TYPE_ALL);
        eassayDetailRecyclerAll.setAdapter(mAllCommentAdapter);
        eassayDetailRecyclerAll.setNestedScrollingEnabled(true);

        // 只能监听 NestedScrollView 滚动了，加载更多评论
        eassayDetailNestedscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    // 向下滑动
                }

                if (scrollY < oldScrollY) {
                    // 向上滑动
                }

                if (scrollY == 0) {
                    // 顶部
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    // 底部
                    if (hasMore) {
                        getData();
                    }
                }
            }
        });
    }

    private void setImageShow() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;

        if (mContentType == Constans.EASSAY_DETAIL_CONTENT_LIST) {//列表
            eassayDetailImageGrid.setVisibility(View.VISIBLE);
            eassayDetailImageContentLayout.setVisibility(View.GONE);

            // 根据图片的数量，显示相应的grid
            ViewGroup.LayoutParams layoutParams = eassayDetailImageGrid.getLayoutParams();
            int imgSize = mImageThumbListBeanList.size();
            if (imgSize == 2 || imgSize == 4) {
                eassayDetailImageGrid.setNumColumns(2);
                if (imgSize <= 2) {
                    layoutParams.height = mScreenWidth / 2 - 4;
                } else {
                    layoutParams.height = mScreenWidth - 4;
                }
            } else {
                eassayDetailImageGrid.setNumColumns(3);
                if (imgSize <= 3) {
                    layoutParams.height = mScreenWidth / 3 - 4;
                } else if (imgSize <= 6) {
                    layoutParams.height = mScreenWidth / 3 * 2 - 4;
                } else {
                    layoutParams.height = mScreenWidth - 4;
                }
            }
            eassayDetailImageGrid.setLayoutParams(layoutParams);

            ImageGridAdapter imageGridAdapter = new ImageGridAdapter(mContext, mImageThumbListBeanList);
            eassayDetailImageGrid.setAdapter(imageGridAdapter);

            eassayDetailImageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra(Constans.IMAGE_LIST, (Serializable) mImageListBeanList);
                    intent.putExtra(Constans.IMAGE_LIST_POSTION, i);
                    mContext.startActivity(intent);
                }
            });
        } else if (mContentType == Constans.EASSAY_DETAIL_CONTENT_TEXT) {//文字
            eassayDetailImageGrid.setVisibility(View.GONE);
            eassayDetailImageContentLayout.setVisibility(View.GONE);
        } else {// 一张图
            eassayDetailImageContentLayout.setVisibility(View.VISIBLE);
            eassayDetailImageGrid.setVisibility(View.GONE);

            // 加载图片
            ImgUtils.loadCircle(mContext, mImageUrl, eassayDetailImageContent);

            if (mContentType == Constans.EASSAY_DETAIL_CONTENT_GIF) {//GIF
                eassayDetailLongImageText.setText(R.string.show_gif_image);
                eassayDetailLongImageText.setVisibility(View.VISIBLE);

                eassayDetailImageContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, SpecialImageActivity.class);
                        intent.putExtra(Constans.SPECIAL_IMAGE_URL, mImageUrl);
                        intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_GIF);
                        mContext.startActivity(intent);
                    }
                });

            } else if (mContentType == Constans.EASSAY_DETAIL_CONTENT_LONG) {//长图
                eassayDetailLongImageText.setText(R.string.show_long_image);
                eassayDetailLongImageText.setVisibility(View.VISIBLE);

                // 因为它是长图，所以修改一下高度
                ViewGroup.LayoutParams layoutParams = eassayDetailImageContent.getLayoutParams();
                layoutParams.width = mScreenWidth;
                layoutParams.height = mScreenWidth / 3 * 4;
                eassayDetailImageContent.setLayoutParams(layoutParams);

                eassayDetailImageContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, SpecialImageActivity.class);
                        intent.putExtra(Constans.SPECIAL_IMAGE_URL, mImageUrl);
                        intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_LONG);
                        mContext.startActivity(intent);
                    }
                });
            } else {//普通图
                eassayDetailLongImageText.setVisibility(View.GONE);

                eassayDetailImageContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, SpecialImageActivity.class);
                        intent.putExtra(Constans.SPECIAL_IMAGE_URL, mImageUrl);
                        intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_NORMAL);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }
}
