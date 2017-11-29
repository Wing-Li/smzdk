package com.lyl.smzdk.ui.news.list.essay;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.NhEassay;
import com.lyl.smzdk.network.entity.YdzxInfo;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.network.imp.news.NhImp;
import com.lyl.smzdk.network.imp.news.YdzxImp;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.news.list.list.ListContentApadter;
import com.lyl.smzdk.ui.web.Html5Activity;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.view.LinearLayoutManagerWrapper;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 内涵段子的页面。
 * 段子、图片
 * <p>
 * Author: lyl
 * Date Created : 2017/11/24.
 */
public class NhEassayListFragment extends BaseFragment {

    /**
     * 列表内容
     */
    public static final String CONTENT_TYPE = "content_type";
    /**
     * 段子
     */
    public static final String CONTENT_TYPE_ESSAY = "-102";
    /**
     * 图片
     */
    public static final String CONTENT_TYPE_IMAGE = "-103";
    /**
     * 一点咨询 - GIF
     */
    public static final String CONTENT_TYPE_YDZX_GIF = "s10671";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private NhImp mNeihanImp;
    private Call<NhEassay> essayCall;
    private YdzxImp mYdzxImp;

    // 页数。
    // 内涵段子，不用页数，每次都是新的
    // 一点咨询，内涵图片需要
    private int page;

    private BaseQuickAdapter mAdapter;
    private boolean mHasMore = true;
    private String mTip;
    private String mContentType;
    private int mScreenWidth;

    private ArrayList<NhEassay.DataBeanX.DataBean> mDataBeen;
    private List<NewInfo> mNewInfoList;


    public static NhEassayListFragment newInstance(String contentType) {
        NhEassayListFragment fragment = new NhEassayListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT_TYPE, contentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        mContentType = bundle.getString(CONTENT_TYPE);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_eassay;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initView();
        getData(true);
    }

    private void initData() {
        mDataBeen = new ArrayList<>();
        mNewInfoList = new ArrayList<>();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getHolder().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    private void getData(final boolean isRefresh) {
        switch (mContentType) {
            case CONTENT_TYPE_ESSAY: // 内涵段子
            case CONTENT_TYPE_IMAGE: // 内涵图片
                getEassayList(isRefresh);
                break;
            case CONTENT_TYPE_YDZX_GIF: // 一点咨询 - GIF
                getYdzxList(isRefresh);
                break;
        }
    }

    /**
     * 获取内涵段子的数据
     *
     * @param isRefresh 下拉刷新
     */
    private void getEassayList(final boolean isRefresh) {
        setRefresh(true);
        if (mNeihanImp == null) {
            mNeihanImp = new NhImp(getHolder());
        }
        essayCall = mNeihanImp.getNhEssayDetails(mContentType);
        Call<NhEassay> clone = essayCall.clone();
        clone.enqueue(new Callback<NhEassay>() {
            @Override
            public void onResponse(Call<NhEassay> call, Response<NhEassay> response) {
                setRefresh(false);
                if (response.isSuccessful()) {
                    NhEassay body = response.body();
                    if ("success".equals(body.getMessage())) {
                        mHasMore = body.getData().isHas_more();
                        mTip = body.getData().getTip();

                        ArrayList<NhEassay.DataBeanX.DataBean> data = (ArrayList<NhEassay.DataBeanX.DataBean>) body
                                .getData().getData();
                        if (data != null && data.size() > 0) {
                            if (isRefresh) {
                                mAdapter.setNewData(data);
                            } else {
                                mAdapter.addData(data);
                            }
                        }
                        mAdapter.loadMoreComplete();
                    }
                    page++;
                } else {
                }
            }

            @Override
            public void onFailure(Call<NhEassay> call, Throwable t) {
                setRefresh(false);
                showToast(R.string.net_error);
                if (t != null) {
                    CrashReport.postCatchedException(t);
                }
            }
        });
    }

    /**
     * 获取一点资讯的数据
     *
     * @param isRefresh 下拉刷新
     */
    private void getYdzxList(final boolean isRefresh) {
        setRefresh(true);
        if (mYdzxImp == null) {
            mYdzxImp = new YdzxImp();
        }

        Call<YdzxInfo> gaoXiaoList = mYdzxImp.getGaoXiaoList(mContentType, page);
        Call<YdzxInfo> clone = gaoXiaoList.clone();
        clone.enqueue(new Callback<YdzxInfo>() {

            @Override
            public void onResponse(Call<YdzxInfo> call, Response<YdzxInfo> response) {
                setRefresh(false);
                if (response.isSuccessful()) {
                    YdzxInfo body = response.body();
                    if (body == null) {
                        showToast(R.string.data_error);
                        return;
                    }
                    if ("success".equals(body.getStatus())) {
                        List<YdzxInfo.ResultBean> result = body.getResult();
                        if (result == null || result.size() <= 0) {
                            return;
                        }
                        // 第一个是目录，删除第一个
                        result.remove(0);

                        List<NewInfo> newInfoList = new ArrayList<>();
                        NewInfo info;
                        for (YdzxInfo.ResultBean bean : result) {
                            info = new NewInfo();
                            info.setTitle(bean.getTitle());
                            info.setImage(bean.getImage());
                            info.setUrl(bean.getUrl());
                            info.setIntroduce(bean.getSummary());
                            info.setTime(bean.getDate());
                            info.setAuthor(bean.getWemedia_info().getName());
                            info.setAuthorIcon(bean.getWemedia_info().getImage());

                            newInfoList.add(info);
                        }

                        if (newInfoList.size() > 0) {
                            if (isRefresh) {
                                mAdapter.setNewData(newInfoList);
                            } else {
                                mAdapter.addData(newInfoList);
                            }
                        }
                        mAdapter.loadMoreComplete();
                    }
                    page++;
                } else {
                }
            }

            @Override
            public void onFailure(Call<YdzxInfo> call, Throwable t) {
                setRefresh(false);
                showToast(R.string.net_error);
                if (t != null) {
                    CrashReport.postCatchedException(t);
                }
            }
        });
    }

    private void initView() {
        switch (mContentType) {
            case CONTENT_TYPE_ESSAY: // 内涵段子
            case CONTENT_TYPE_IMAGE: // 内涵图片
                mAdapter = new NhEassayListAdapter(mDataBeen, mContentType, mScreenWidth);
                break;
            case CONTENT_TYPE_YDZX_GIF: // 一点咨询 - GIF
                mAdapter = new ListContentApadter(mNewInfoList, Constans.SHOW_ITEM_CONTENT_2);
                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                        NewInfo info = (NewInfo) baseQuickAdapter.getItem(i);

                        Intent intent = new Intent(getHolder(), Html5Activity.class);
                        intent.putExtra(Constans.I_URL, info.getUrl());
                        intent.putExtra(Constans.I_TITLE, info.getTitle());
                        intent.putExtra(Constans.I_CHANNEL_TYPE_TYPE, Constans.NEWS_TYPE_XIUXIAN);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            View titleView = view.findViewById(R.id.item_main_content_title);
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create
                                    (titleView, "content_title"));
                            startActivity(intent, options.toBundle());
                        } else {
                            startActivity(intent);
                        }
                    }
                });

                // 设置每个 Item 中间横线
                recyclerView.addItemDecoration(new DividerItemDecoration(getHolder(), DividerItemDecoration.VERTICAL));
                break;
        }

        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getHolder()));
        recyclerView.setAdapter(mAdapter);

        // 加载更多
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, recyclerView);

        // 下拉刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHasMore = true;
                page = 1;
                getData(true);
            }
        });
    }

    private void setRefresh(boolean isShow) {
        if (swipeRefresh != null) {
            swipeRefresh.setRefreshing(isShow);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (essayCall != null && !essayCall.isCanceled()) {
            essayCall.cancel();
        }
        ImgUtils.cancelAllTasks(getHolder().getApplicationContext());
    }
}
