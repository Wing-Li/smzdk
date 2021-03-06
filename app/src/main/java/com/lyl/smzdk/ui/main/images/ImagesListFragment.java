package com.lyl.smzdk.ui.main.images;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.images.Album;
import com.lyl.smzdk.network.entity.images.ImageInfo;
import com.lyl.smzdk.network.entity.myapi.BaseCallBack;
import com.lyl.smzdk.network.imp.MyApiImp;
import com.lyl.smzdk.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

public class ImagesListFragment extends BaseFragment {

    private static final String IMAGE_LIST_TYPE = "image_list_type";
    private static final String IMAGE_LIST_TITLE = "image_list_title";

    @BindView(R.id.image_listview)
    RecyclerView imageListview;
    @BindView(R.id.image_swiperefresh)
    SwipeRefreshLayout imageSwiperefresh;

    private String mTitle;
    private String mType;

    private List<ImageInfo> mInfoList;
    private ImagesListApapter mImagesListApapter;
    private boolean isRefresh;

    private int page;

    public static ImagesListFragment newInstance(String type, String title) {
        ImagesListFragment fragment = new ImagesListFragment();
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
        mType = bundle.getString(IMAGE_LIST_TYPE);
        mTitle = bundle.getString(IMAGE_LIST_TITLE);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_images_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListView();
        page = 1;
        loadData();
    }

    private void setListView() {
        mInfoList = new ArrayList<>();
        mImagesListApapter = new ImagesListApapter(R.layout.item_image_list, mInfoList);
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
                ImageInfo imageInfo = (ImageInfo) baseQuickAdapter.getItem(i);
                if (imageInfo == null) {
                    showToast(R.string.data_error);
                    return;
                }

                Intent intent = new Intent(getHolder(), SummaryImagesActivity.class);
                intent.putExtra(SummaryImagesActivity.IMAGE_LIST_ALBUM_ID, imageInfo.getAlbumId());
                intent.putExtra(SummaryImagesActivity.IMAGE_LIST_TITLE, imageInfo.getTitle());
                startActivity(intent);
            }
        });

        imageListview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        imageListview.setAdapter(mImagesListApapter);

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
        showRefresh();

        Observable<BaseCallBack<List<Album>>> albumRandomList = Network.getMyMnApi().getAlbumRandomList(Long.parseLong(mType));
        new MyApiImp<List<Album>>().request(albumRandomList, new MyApiImp.NetWorkCallBack<List<Album>>() {
            @Override
            public void onSuccess(List<Album> albumList) {
                if (albumList != null && albumList.size() > 0) {

                    List<ImageInfo> data = new ArrayList<>();
                    ImageInfo imageInfo;
                    for (Album album : albumList) {
                        imageInfo = new ImageInfo();
                        imageInfo.setAlbumId(album.getId());
                        imageInfo.setTitle(album.getName());
                        imageInfo.setPic_url(album.getImageUrl());
                        data.add(imageInfo);
                    }

                    if (isRefresh) {
                        mImagesListApapter.setNewData(data);
                        isRefresh = false;
                    } else {
                        mImagesListApapter.addData(data);
                    }
                    page ++;
                    mImagesListApapter.loadMoreComplete();
                }
                closeRefresh();
            }

            @Override
            public void onFail(int code, String msg) {
                getHolder().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(R.string.net_error);
                        closeRefresh();
                    }
                });
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
