package com.lyl.smzdk.ui.news.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.news.MainContentApadter;
import com.lyl.smzdk.view.LinearLayoutManagerWrapper;

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

    private int mChannelType;
    private String mMenuType;

    private List<NewInfo> mNewInfos;
    private MainContentApadter mContentApadter;


    /**
     * @param channelType 频道类型。 微信精选、一点资讯
     * @param menuType    频道底下的二级目录类型
     * @return
     */
    public static ListFragment newInstance(int channelType, String menuType) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constans.I_CHANNEL_TYPE_TYPE, channelType);
        bundle.putString(Constans.I_MENU_LIST_TYPE, menuType);
        listFragment.setArguments(bundle);

        return listFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        mChannelType = arguments.getInt(Constans.I_CHANNEL_TYPE_TYPE);
        mMenuType = arguments.getString(Constans.I_MENU_LIST_TYPE);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initAdapter();

        mPresenter = new ListPresenter(this);
        mPresenter.reLoadData(mChannelType, mMenuType);
    }

    @Override
    public void setData(List<NewInfo> newInfos) {
        mContentApadter.addData(newInfos);
        mContentApadter.loadMoreComplete();
    }

    private void initAdapter() {
        mContentApadter = new MainContentApadter(mNewInfos);
        mContentApadter.setDuration(BaseQuickAdapter.SLIDEIN_RIGHT);
        mContentApadter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadData(mChannelType, mMenuType);
            }
        }, recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManagerWrapper(getHolder()));
        recyclerview.addItemDecoration(new DividerItemDecoration(getHolder(), DividerItemDecoration.VERTICAL));
        recyclerview.setAdapter(mContentApadter);
    }

    @Override
    public void setLoading() {

    }
}
