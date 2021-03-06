package com.lyl.smzdk.ui.search;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.BtLoadDataEvent;
import com.lyl.smzdk.network.entity.bt.BtInfo;
import com.lyl.smzdk.network.imp.bt.BtImp;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.utils.MyUtils;
import com.lyl.smzdk.view.layoutmanager.LinearLayoutManagerWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: lyl
 * Date Created : 2017/11/15.
 */
public class SearchListFragment extends BaseFragment {

    private static final String SEARCH_LIST_TYPE = "SEARCH_LIST_TYPE";
    private static final String SEARCH_LIST_CONTENT = "SEARCH_LIST_CONTENT";

    @BindView(R.id.search_sw)
    SwipeRefreshLayout swLayout;
    @BindView(R.id.search_listview)
    RecyclerView searchListview;

    private String mContent;
    private String mType;

    private List<BtInfo> mInfoList;
    private SearchListAdapter mSearchListAdapter;
    private int page = 1;

    public static SearchListFragment newInstance(String type, String content) {
        SearchListFragment fragment = new SearchListFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_LIST_TYPE, type);
        args.putString(SEARCH_LIST_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        mType = arguments.getString(SEARCH_LIST_TYPE);
        mContent = arguments.getString(SEARCH_LIST_CONTENT);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mSearchListAdapter.getData().size() <= 0) {
            loadData();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
        searchListview.addItemDecoration(new DividerItemDecoration(getHolder(), DividerItemDecoration.VERTICAL));
        searchListview.setAdapter(mSearchListAdapter);

        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                clearData();
                openRefresh();
                loadData();
            }
        });

        mSearchListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BtInfo info = (BtInfo) baseQuickAdapter.getItem(i);
                if (info == null) {
                    showToast(R.string.data_error);
                    return;
                }

                switch (view.getId()) {
                    case R.id.item_search_copy:
                        copyUrl(info.getBtUrl());
                        break;
                    case R.id.item_search_download:
                        downloadUrl(info.getBtUrl());
                        break;
                    case R.id.item_search_share:
                        MyUtils.shareApp(getHolder(), info.getName(), info.getBtUrl());
                        break;
                }
            }

            private void copyUrl(String url) {
                ClipboardManager clipboardManager = (ClipboardManager) getHolder().getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboardManager != null) {
                    ClipData clipData = ClipData.newPlainText("test", url);
                    clipboardManager.setPrimaryClip(clipData);

                    showToast("已复制到粘贴板");
                } else {
                    showToast("粘贴板获取错误，请尝试直接下载");
                }
            }

            private void downloadUrl(String url) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    MyUtils.copyText(getHolder(), url);
                    showToast("没有程序能提供下载功能，已为您将链接复制到剪切板");
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void searchContent(BtLoadDataEvent dataEvent) {
        // 数据换了，先把以前的从页面清除掉
        if (!mContent.equals(dataEvent.content)) {
            clearData();
        }

        mContent = dataEvent.content;
        page = 1;
        openRefresh();
        loadData();
    }

    private void loadData() {
        if (TextUtils.isEmpty(mContent)) {
            clearData();
            return;
        }

        Observable.create(new ObservableOnSubscribe<List<BtInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BtInfo>> observableEmitter) throws Exception {
                List<BtInfo> btInfos = new ArrayList<>();
                switch (mType) {
                    case Constans.BT_TYPE_1:
                        btInfos = BtImp.getList(mContent, page);
                        break;
                    case Constans.BT_TYPE_2:
                        btInfos = BtImp.getList2(mContent, page);
                        break;
                    case Constans.BT_TYPE_3:
                        btInfos = BtImp.getList3(mContent, page);
                        break;
                    case Constans.BT_TYPE_4:
                        btInfos = BtImp.getList4(mContent, page);
                        break;
                }

                observableEmitter.onNext(btInfos);
            }
        })//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<List<BtInfo>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(List<BtInfo> btInfos) {
                        if (btInfos != null) {
                            if (btInfos.size() > 0) {
                                if (page == 1) {
                                    mSearchListAdapter.setNewData(btInfos);
                                } else {
                                    mSearchListAdapter.addData(btInfos);
                                }
                                mSearchListAdapter.loadMoreComplete();
                            } else {
                                // 返回的数据是 0 个：1. 空数据；2. 加载完了。
                                if (mSearchListAdapter.getItemCount() <= 0) {
                                    mSearchListAdapter.setEmptyView(R.layout.layout_empty_view, searchListview);
                                } else {
                                    mSearchListAdapter.loadMoreEnd();
                                }
                            }

                            closeRefresh();
                        }

                        page++;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getHolder().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeRefresh();
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void clearData() {
        closeRefresh();
        mSearchListAdapter.getData().clear();
        mSearchListAdapter.notifyDataSetChanged();
    }

    private void closeRefresh() {
        if (swLayout != null && swLayout.isRefreshing()) {
            swLayout.setRefreshing(false);
        }
    }

    private void openRefresh() {
        if (swLayout != null && !swLayout.isRefreshing()) {
            swLayout.setRefreshing(true);
        }
    }
}
