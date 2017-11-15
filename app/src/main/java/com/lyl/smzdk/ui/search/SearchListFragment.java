package com.lyl.smzdk.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.SearchInfo;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.view.LinearLayoutManagerWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: lyl
 * Date Created : 2017/11/15.
 */
public class SearchListFragment extends BaseFragment {

    private static final String SEARCH_LIST_TYPE = "SEARCH_LIST_TYPE";

    @BindView(R.id.search_listview)
    RecyclerView searchListview;

    private String mType;
    private List<SearchInfo> mInfoList;
    private SearchListAdapter mSearchListAdapter;


    public static SearchListFragment newInstance(String type) {
        SearchListFragment fragment = new SearchListFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_LIST_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        mType = arguments.getString(SEARCH_LIST_TYPE);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListView();
        loadData();
    }

    private void setListView() {
        mInfoList = new ArrayList<>();
        mSearchListAdapter = new SearchListAdapter(R.layout.item_search_list, mInfoList);
        mSearchListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, searchListview);

        searchListview.setLayoutManager(new LinearLayoutManagerWrapper(getHolder()));
        searchListview.addItemDecoration(new DividerItemDecoration(getHolder(),DividerItemDecoration.VERTICAL));
        searchListview.setAdapter(mSearchListAdapter);
    }

    private void loadData() {
        switch (mType) {
            case "10001":
                break;
        }

        SearchInfo info;
        for (int i = 0; i < 10; i++) {
            info = new SearchInfo();
            info.setTitle("饺子这样不好");
            info.setUrl("http://jzvd.nathen.cn/b201be3093814908bf987320361c5a73/2f6d913ea25941ffa78cc53a59025383" +
                    "-5287d2089db37e62345123a1be272f8b.mp4");
            mSearchListAdapter.addData(info);
        }
        mSearchListAdapter.loadMoreComplete();
    }
}
