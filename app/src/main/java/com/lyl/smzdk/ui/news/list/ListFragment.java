package com.lyl.smzdk.ui.news.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.news.MainContentApadter;

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

    private String mType;

    private List<NewInfo> mNewInfos;
    private MainContentApadter mContentApadter;


    public static ListFragment newInstance(String type) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.I_MENU_LIST_TYPE, type);
        listFragment.setArguments(bundle);

        return listFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        mType = arguments.getString(Constans.I_MENU_LIST_TYPE);
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
        mPresenter.loadData(mType);
    }

    @Override
    public void setData(List<NewInfo> newInfos) {

        mContentApadter.addData(newInfos);
        mContentApadter.loadMoreComplete();
    }

    private void initAdapter() {
        mContentApadter = new MainContentApadter(mNewInfos);
        mContentApadter.setDuration(BaseQuickAdapter.ALPHAIN);
        mContentApadter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadData(mType);
            }
        }, recyclerview);
    }

    @Override
    public void setLoading() {

    }
}
