package com.lyl.smzdk.ui.main.images;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.HideBottombarEvent;
import com.lyl.smzdk.network.entity.news.GifInfo;
import com.lyl.smzdk.network.imp.news.GifImp;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.image.SpecialImageActivity;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Deprecated
public class GifListFragment extends BaseFragment {

    private static final String IMAGE_LIST_TYPE = "image_list_type";
    private static final String IMAGE_LIST_TITLE = "image_list_title";

    @BindView(R.id.image_swiperefresh)
    SwipeRefreshLayout imageSwiperefresh;
    @BindView(R.id.summary_listview)
    RecyclerView summaryListview;
    @BindView(R.id.image_listview)
    RecyclerView imageListview;

    private GifImp mGifImp;
    private String mTitle;
    private String mType;

    private List<GifInfo.CatesBean> mSummaryList;
    private GifListSummaryApapter mGifSummaryApapter;

    private List<GifInfo.ItemsBean> mInfoList;
    private GifListApapter mImagesListApapter;
    private boolean isRefresh;

    private int page;
    private int count = 20;

    public static GifListFragment newInstance(String type, String title) {
        GifListFragment fragment = new GifListFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_LIST_TYPE, type);
        args.putString(IMAGE_LIST_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString(IMAGE_LIST_TYPE);
            mTitle = bundle.getString(IMAGE_LIST_TITLE);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_gif_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListView();
        page = 1;
        loadData();
    }

    private void setListView() {
        mSummaryList = new ArrayList<>();
        mInfoList = new ArrayList<>();

        mGifSummaryApapter = new GifListSummaryApapter(R.layout.item_gif_summary, mSummaryList);
        mGifSummaryApapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                GifInfo.CatesBean imageInfo = (GifInfo.CatesBean) baseQuickAdapter.getItem(i);
                if (imageInfo == null) {
                    showToast(R.string.data_error);
                    return;
                }

                Intent intent = new Intent(getHolder(), GifSummarysActivity.class);
                intent.putExtra(GifSummarysActivity.IMAGE_LIST_TYPE, imageInfo.getName());
                intent.putExtra(GifSummarysActivity.IMAGE_LIST_TITLE, imageInfo.getName());
                startActivity(intent);
            }
        });
        summaryListview.setLayoutManager(new LinearLayoutManager(getHolder(), LinearLayoutManager.HORIZONTAL, false));
        summaryListview.setAdapter(mGifSummaryApapter);

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

                Intent intent = new Intent(getHolder(), SpecialImageActivity.class);
                intent.putExtra(Constans.SPECIAL_IMAGE_URL, imageInfo.getPicUrl());
                if (imageInfo.getHeight() > 2500) {
                    intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_LONG);
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    View imagePlayView = view.findViewById(R.id.item_images_img);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getHolder(), Pair.create
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

        Call<GifInfo> gifInfoCall = mGifImp.getGifs(mType, (page - 1) * count, page * count);
        Call<GifInfo> clone = gifInfoCall.clone();
        clone.enqueue(new Callback<GifInfo>() {
            @Override
            public void onResponse(Call<GifInfo> call, Response<GifInfo> response) {
                if (response.isSuccessful() && response.body() != null){

                    //  目录没有被添加过，就将数据添加
                    List<GifInfo.CatesBean> data = mGifSummaryApapter.getData();
                    if (data.size() == 0){
                        List<GifInfo.CatesBean> cates = response.body().getCates();
                        if (cates != null && cates.size() > 0){
                            mGifSummaryApapter.setNewData(cates);
                            mGifSummaryApapter.loadMoreComplete();
                        }
                    }

                    // 图片列表
                    List<GifInfo.ItemsBean> imageInfos = response.body().getItems();
                    if (imageInfos != null && imageInfos.size() > 0) {
                        if (isRefresh) {
                            mImagesListApapter.setNewData(imageInfos);
                            isRefresh = false;
                        } else {
                            mImagesListApapter.addData(imageInfos);
                        }
                        page++;
                        mImagesListApapter.loadMoreComplete();
                    }
                    closeRefresh();
                }
            }

            @Override
            public void onFailure(Call<GifInfo> call, Throwable throwable) {
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
