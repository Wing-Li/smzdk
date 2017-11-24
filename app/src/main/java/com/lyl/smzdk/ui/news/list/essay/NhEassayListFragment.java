package com.lyl.smzdk.ui.news.list.essay;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.NhEassay;
import com.lyl.smzdk.network.imp.news.NhImp;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.view.LinearLayoutManagerWrapper;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;

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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private NhImp mNeihanImp;
    private Call<NhEassay> essayCall;

    private NhEassayListAdapter mAdapter;
    private boolean mHasMore = true;
    private String mTip;
    private String mContentType;
    private int mScreenWidth;

    private ArrayList<NhEassay.DataBeanX.DataBean> mDataBeen;


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
        getData(true);
        initView();
    }

    private void initData() {
        mDataBeen = new ArrayList<>();
        mNeihanImp = new NhImp(getHolder());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getHolder().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    private void getData(final boolean isRefresh) {
        swipeRefresh.setRefreshing(true);
        essayCall = mNeihanImp.getNhEssayDetails(mContentType);
        Call<NhEassay> clone = essayCall.clone();
        clone.enqueue(new Callback<NhEassay>() {
            @Override
            public void onResponse(Call<NhEassay> call, Response<NhEassay> response) {
                swipeRefresh.setRefreshing(false);
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
                } else {
                }
            }

            @Override
            public void onFailure(Call<NhEassay> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                showToast(R.string.net_error);
                if (t != null) {
                    CrashReport.postCatchedException(t);
                }
            }
        });
    }

    private void initView() {
        int title = 0;
        if (CONTENT_TYPE_ESSAY.equals(mContentType)) {
            title = R.string.nheassay;
        } else if (CONTENT_TYPE_IMAGE.equals(mContentType)) {
            title = R.string.nheassay_image;
        }

        mAdapter = new NhEassayListAdapter(mDataBeen, mContentType, mScreenWidth);
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
                getData(true);
            }
        });
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
