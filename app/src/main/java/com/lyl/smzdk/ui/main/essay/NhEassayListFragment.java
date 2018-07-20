package com.lyl.smzdk.ui.main.essay;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.main.news.list.ListContentApadter;
import com.lyl.smzdk.ui.web.Html5Activity;
import com.lyl.smzdk.view.LinearLayoutManagerWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
     * 一点咨询 - GIF
     */
    public static final String CONTENT_TYPE_YDZX_GIF = "s10671";
    public static final String CONTENT_TYPE_YDZX_GIF2 = "u11272";
    public static final String CONTENT_TYPE_YDZX_GIF3 = "m378250";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    // 页数。
    // 内涵段子，不用页数，每次都是新的
    // 一点咨询，内涵图片需要
    private int page;

    private BaseQuickAdapter mAdapter;
    private boolean mHasMore = true;
    private String mTip;
    private String mContentType;
    private int mScreenWidth;

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
        getData(false);
    }

    private void initData() {
        mNewInfoList = new ArrayList<>();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getHolder().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    private void getData(final boolean isRefresh) {
        getYdzxList(isRefresh);// 一点咨询 - GIF
    }


    /**
     * 获取列表的数据
     *
     * @param isRefresh 下拉刷新
     */
    private void getYdzxList(final boolean isRefresh) {
//        setRefresh(true);
//        if (mYdzxImp == null) {
//            mYdzxImp = new YdzxImp();
//        }
//
//        Call<YdzxInfo> gaoXiaoList = mYdzxImp.getGaoXiaoList(mContentType, page);
//        Call<YdzxInfo> clone = gaoXiaoList.clone();
//        clone.enqueue(new Callback<YdzxInfo>() {
//
//            @Override
//            public void onResponse(Call<YdzxInfo> call, Response<YdzxInfo> response) {
//                setRefresh(false);
//                if (response.isSuccessful()) {
//                    YdzxInfo body = response.body();
//                    if (body == null) {
//                        showToast(R.string.data_error);
//                        return;
//                    }
//                    if ("success".equals(body.getStatus())) {
//                        List<YdzxInfo.ResultBean> result = body.getResult();
//                        if (result == null || result.size() <= 0) {
//                            return;
//                        }
//                        // 第一个是目录，删除第一个
//                        result.remove(0);
//
//                        List<NewInfo> newInfoList = new ArrayList<>();
//                        NewInfo info;
//                        for (YdzxInfo.ResultBean bean : result) {
//                            info = new NewInfo();
//                            info.setTitle(bean.getTitle());
//                            info.setImage(bean.getImage());
//                            info.setUrl(bean.getUrl());
//                            info.setIntroduce(bean.getSummary());
//                            info.setTime(bean.getDate());
//                            YdzxInfo.ResultBean.WemediaInfoBean wemedia_info = bean.getWemedia_info();
//                            if (wemedia_info != null) {
//                                info.setAuthor(wemedia_info.getName());
//                                info.setAuthorIcon(wemedia_info.getImage());
//                            }
//
//                            newInfoList.add(info);
//                        }
//
//                        if (newInfoList.size() > 0) {
//                            if (isRefresh) {
//                                mAdapter.setNewData(newInfoList);
//                            } else {
//                                mAdapter.addData(newInfoList);
//                            }
//                        }
//                        mAdapter.loadMoreComplete();
//                    }
//                    page++;
//                } else {
//                }
//            }
//
//            @Override
//            public void onFailure(Call<YdzxInfo> call, Throwable t) {
//                setRefresh(false);
//                showToast(R.string.net_error);
//                if (t != null) {
//                    CrashReport.postCatchedException(t);
//                }
//            }
//        });
    }

    private void initView() {
        mAdapter = new ListContentApadter(mNewInfoList, Constans.SHOW_ITEM_CONTENT_2);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NewInfo info = (NewInfo) baseQuickAdapter.getItem(i);

                if (info == null) {
                    showToast(R.string.data_error);
                    return;
                }

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

                TextView titleView = (TextView) baseQuickAdapter.getViewByPosition(i, R.id.item_main_content_title);
                if (titleView != null) {
                    titleView.setTextColor(ContextCompat.getColor(getHolder(), R.color.black_flee_two));
                }

                TextView introduceView = (TextView) baseQuickAdapter.getViewByPosition(i, R.id
                        .item_main_content_introduce);
                if (introduceView != null) {
                    introduceView.setTextColor(ContextCompat.getColor(getHolder(), R.color.black_flee_three));
                }
            }
        });

        // 设置每个 Item 中间横线
        recyclerView.addItemDecoration(new DividerItemDecoration(getHolder(), DividerItemDecoration.VERTICAL));

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
    }
}
