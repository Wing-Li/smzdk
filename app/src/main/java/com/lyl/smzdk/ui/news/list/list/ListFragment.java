package com.lyl.smzdk.ui.news.list.list;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.web.Html5Activity;

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

    private List<NewInfo> mNewInfos;
    private ListContentApadter mContentApadter;


    /**
     * @param channelType 频道类型。 微信精选、一点资讯
     * @param menuType    频道底下的二级目录类型
     * @return
     */
    public static ListFragment newInstance(String channelType, String menuType, int showItemType) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.I_CHANNEL_TYPE_TYPE, channelType);
        bundle.putString(Constans.I_MENU_LIST_TYPE, menuType);
        bundle.putInt(Constans.I_LIST_ITEM_SHOW_TYPE, showItemType);
        listFragment.setArguments(bundle);

        return listFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        mChannelType = arguments.getString(Constans.I_CHANNEL_TYPE_TYPE);
        mMenuType = arguments.getString(Constans.I_MENU_LIST_TYPE);
        mShowItemType = arguments.getInt(Constans.I_LIST_ITEM_SHOW_TYPE, Constans.ITEM_CONTENT_1);
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
        mPresenter.reLoadData(mChannelType, mMenuType);
    }

    @Override
    public void setData(List<NewInfo> newInfos) {
        mContentApadter.addData(newInfos);
        mContentApadter.loadMoreComplete();
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
        recyclerview.addItemDecoration(new DividerItemDecoration(getHolder(), DividerItemDecoration.VERTICAL));
        recyclerview.setAdapter(mContentApadter);

        // 设置单击事件
        mContentApadter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NewInfo info = (NewInfo) baseQuickAdapter.getItem(i);

                Intent intent = new Intent(getHolder(), Html5Activity.class);
                intent.putExtra(Constans.I_WEB_URL, info.getUrl());
                intent.putExtra(Constans.I_WEB_TITLE, info.getTitle());

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
    }

    @Override
    public void setLoading() {

    }
}
