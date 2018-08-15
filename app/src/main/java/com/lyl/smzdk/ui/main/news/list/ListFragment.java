package com.lyl.smzdk.ui.main.news.list;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.MainLoadDataEvent;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.image.SpecialImageActivity;
import com.lyl.smzdk.ui.web.Html5Activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: lyl
 * Date Created : 2017/11/20.
 */
public class ListFragment extends BaseFragment implements ListContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private ListPresenter mPresenter;

    private String mChannelType;
    private String mMenuType;
    private int mShowItemType;
    private boolean mIsDecoration;

    private List<NewInfo> mNewInfos;
    private ListContentApadter mContentApadter;


    /**
     * @param channelType  频道类型。 微信、知乎、读者、闲读
     * @param menuType     频道底下的二级目录类型
     * @param showItemType 列表样式显示的类型
     * @param isDecoration 列表间 是否有分割线
     */
    public static ListFragment newInstance(String channelType, String menuType, int showItemType, boolean isDecoration) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.I_CHANNEL_TYPE_TYPE, channelType);
        bundle.putString(Constans.I_MENU_LIST_TYPE, menuType);
        bundle.putInt(Constans.I_LIST_ITEM_SHOW_TYPE, showItemType);
        bundle.putBoolean(Constans.I_LIST_ITEM_DECORATION, isDecoration);
        listFragment.setArguments(bundle);

        return listFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mChannelType = arguments.getString(Constans.I_CHANNEL_TYPE_TYPE);
            mMenuType = arguments.getString(Constans.I_MENU_LIST_TYPE);
            mShowItemType = arguments.getInt(Constans.I_LIST_ITEM_SHOW_TYPE, Constans.SHOW_ITEM_CONTENT_1);
            mIsDecoration = arguments.getBoolean(Constans.I_LIST_ITEM_DECORATION);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initAdapter();

        mNewInfos = new ArrayList<>();
        mPresenter = new ListPresenter(this);
        showDialog();
        mPresenter.reLoadData(mChannelType, mMenuType);
    }

    @Override
    public void setData(List<NewInfo> newInfos) {
        hideDialog();
        mContentApadter.addData(newInfos);
        mContentApadter.loadMoreComplete();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadMoreData(MainLoadDataEvent event) {
        recyclerview.smoothScrollToPosition(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initAdapter() {
        // 设置Item
        mContentApadter = new ListContentApadter(mNewInfos, mShowItemType);
        mContentApadter.setDuration(BaseQuickAdapter.SLIDEIN_RIGHT);
        mContentApadter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadData(mChannelType, mMenuType);
            }
        }, recyclerview);

        // 设置 RecyclerView
        recyclerview.setLayoutManager(new LinearLayoutManager(getHolder()));
        if (mIsDecoration) recyclerview.addItemDecoration(new DividerItemDecoration(getHolder(), DividerItemDecoration.VERTICAL));
        recyclerview.setAdapter(mContentApadter);
        // 主页的列表隐藏底部 bar
        if (Constans.NEWS_TYPE_WABA.equals(mChannelType)) recyclerview.addOnScrollListener(mOnScrollHideBottombarListener);

        // 设置单击事件
        mContentApadter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NewInfo info = (NewInfo) baseQuickAdapter.getItem(i);
                if (info == null) return;

                // 如果链接是空的，不能跳转链接
                if (TextUtils.isEmpty(info.getUrl())) {
                    // 如果图片不是空的，就显示图片
                    if (!TextUtils.isEmpty(info.getImage())) {
                        Intent intent = new Intent(getHolder(), SpecialImageActivity.class);
                        intent.putExtra(Constans.SPECIAL_IMAGE_URL, info.getImage());
                        intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_NORMAL);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            View imagePlayView = view.findViewById(R.id.item_main_content_image);
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getHolder(), Pair.create(imagePlayView,
                                    "image"));
                            startActivity(intent, options.toBundle());
                        } else {
                            startActivity(intent);
                        }
                    }

                } else {
                    // 跳转链接
                    Intent intent = new Intent(getHolder(), Html5Activity.class);
                    intent.putExtra(Constans.I_URL, info.getUrl());
                    intent.putExtra(Constans.I_TITLE, info.getTitle());
                    intent.putExtra(Constans.I_CHANNEL_TYPE_TYPE, mChannelType);

                    // 跳转页面时的共享元素
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        View titleView = view.findViewById(R.id.item_main_content_title);
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create(titleView,
                                "content_title"));
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }

                    // 点击之后，将看过的条目设置成灰色的，代表已看过。
                    TextView titleView = (TextView) baseQuickAdapter.getViewByPosition(i, R.id.item_main_content_title);
                    if (titleView != null) {
                        titleView.setTextColor(ContextCompat.getColor(getHolder(), R.color.black_flee_two));
                    }
                    TextView introduceView = (TextView) baseQuickAdapter.getViewByPosition(i, R.id.item_main_content_introduce);
                    if (introduceView != null) {
                        introduceView.setTextColor(ContextCompat.getColor(getHolder(), R.color.black_flee_three));
                    }
                }
            }
        });
    }

    /**
     * 判断当前Fragment是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
    }

    private ProgressDialog mProgressDialog;
    private boolean mIsVisibleToUser;

    /**
     * 显示加载圈
     */
    protected void showDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getHolder());
            mProgressDialog.setMessage("可爱的手机在努力加载......");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        if (mProgressDialog != null && mIsVisibleToUser) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏加载进度圈
     */
    protected void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
