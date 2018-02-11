package com.lyl.smzdk.ui.main.images;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.HideBottombarEvent;
import com.lyl.smzdk.network.entity.news.GifInfo;
import com.lyl.smzdk.network.entity.news.GifSummaryInfo;
import com.lyl.smzdk.network.imp.news.GifImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.ui.image.SpecialImageActivity;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GifSummarysActivity extends BaseActivity {

    public static final String IMAGE_LIST_TYPE = "image_list_type";
    public static final String IMAGE_LIST_TITLE = "image_list_title";

    @BindView(R.id.image_swiperefresh)
    SwipeRefreshLayout imageSwiperefresh;
    @BindView(R.id.summary_listview)
    RecyclerView summaryListview;
    @BindView(R.id.image_listview)
    RecyclerView imageListview;

    private GifImp mGifImp;
    private String mTitle;
    private String mType;

    private List<GifInfo.ItemsBean> mInfoList;
    private GifListApapter mImagesListApapter;
    private boolean isRefresh;

    private int page;
    private final int count = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gif_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mType = intent.getStringExtra(IMAGE_LIST_TYPE);
        mTitle = intent.getStringExtra(IMAGE_LIST_TITLE);

        setListView();
        page = 1;
        loadData();
    }

    private void setListView() {
        mInfoList = new ArrayList<>();
        summaryListview.setVisibility(View.GONE);

        mImagesListApapter = new GifListApapter(R.layout.item_image_list, mInfoList);
        mImagesListApapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mImagesListApapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, imageListview);
        mImagesListApapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                GifInfo.ItemsBean imageInfo = (GifInfo.ItemsBean) baseQuickAdapter.getItem(i);
                if (imageInfo == null) {
                    showToast(R.string.data_error);
                    return;
                }

                Intent intent = new Intent(mContext, SpecialImageActivity.class);
                intent.putExtra(Constans.SPECIAL_IMAGE_URL, imageInfo.getPicUrl());
                if (imageInfo.getHeight() > 2500) {
                    intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_LONG);
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    View imagePlayView = view.findViewById(R.id.item_images_img);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, Pair.create
                            (imagePlayView, "image"));
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        imageListview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        imageListview.setAdapter(mImagesListApapter);
        imageListview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy >= 5) {// 手指向上滚动
                    EventBus.getDefault().post(new HideBottombarEvent(true));
                } else if (dy <= -5) {// 手指向下滚动
                    EventBus.getDefault().post(new HideBottombarEvent(false));
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        imageSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                loadData();
            }
        });
    }

    private void loadData() {
        if (mGifImp == null) {
            mGifImp = new GifImp();
        }

        showRefresh();

        Call<GifSummaryInfo> gifInfoCall = mGifImp.getGifSummarys(mType, (page - 1) * count, page * count);
        Call<GifSummaryInfo> clone = gifInfoCall.clone();
        clone.enqueue(new Callback<GifSummaryInfo>() {
            @Override
            public void onResponse(Call<GifSummaryInfo> call, Response<GifSummaryInfo> response) {
                GifSummaryInfo body = response.body();
                if (response.isSuccessful() && body != null) {

                    // 图片列表
                    List<GifSummaryInfo.ItemsBean> imageInfos = body.getItems();

                    // 将子目录请求的结果转化为 GifInfo 下的，为了跟 GifInfo 重用一个 Adapter
                    List<GifInfo.ItemsBean> gifInfoList = new ArrayList<>();
                    GifInfo.ItemsBean gifinfo;
                    for (GifSummaryInfo.ItemsBean itemsBean : imageInfos) {
                        gifinfo = new GifInfo.ItemsBean();
                        gifinfo.setTitle(itemsBean.getTitle());
                        gifinfo.setPicUrl(itemsBean.getPicUrl());
                        gifInfoList.add(gifinfo);
                    }

                    if (imageInfos.size() > 0) {
                        if (isRefresh) {
                            mImagesListApapter.setNewData(gifInfoList);
                            isRefresh = false;
                        } else {
                            mImagesListApapter.addData(gifInfoList);
                        }
                        page++;
                        mImagesListApapter.loadMoreComplete();
                    }
                    closeRefresh();
                }
            }

            @Override
            public void onFailure(Call<GifSummaryInfo> call, Throwable throwable) {
                if (throwable != null) {
                    CrashReport.postCatchedException(throwable);
                }
                closeRefresh();
            }
        });
    }

    private void closeRefresh() {
        if (imageSwiperefresh != null && imageSwiperefresh.isRefreshing()) {
            imageSwiperefresh.setRefreshing(false);
        }
    }

    private void showRefresh() {
        if (imageSwiperefresh != null && !imageSwiperefresh.isRefreshing()) {
            imageSwiperefresh.setRefreshing(true);
        }
    }
}
