package com.lyl.smzdk.ui.images;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.HideBottombarEvent;
import com.lyl.smzdk.network.entity.images.ImageInfo;
import com.lyl.smzdk.network.entity.images.ImgsCall;
import com.lyl.smzdk.network.imp.images.ImgsImp;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.image.SpecialImageActivity;
import com.lyl.smzdk.utils.NetUtil;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagesListFragment extends BaseFragment {

    private static final String IMAGE_LIST_TYPE = "image_list_type";
    private static final String IMAGE_LIST_TITLE = "image_list_title";

    @BindView(R.id.image_listview)
    RecyclerView imageListview;
    @BindView(R.id.image_swiperefresh)
    SwipeRefreshLayout imageSwiperefresh;

    private ImgsImp mImgsImp;
    private String mTitle;
    private String mType;

    private List<ImageInfo> mInfoList;
    private ImagesListApapter mImagesListApapter;
    private boolean isRefresh;

    private int start;
    private int len = 15;

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

                Intent intent = new Intent(getHolder(), SpecialImageActivity.class);
                intent.putExtra(Constans.SPECIAL_IMAGE_URL, imageInfo.getPic_url());
                if (imageInfo.getHeight() > 2500) {
                    intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_LONG);
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    View imagePlayView = view.findViewById(R.id.item_images_img);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create
                            (imagePlayView, "image"));
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        imageListview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        imageListview.setAdapter(mImagesListApapter);
        imageListview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy >= 5) {// 手指向上滚动
                    EventBus.getDefault().post(new HideBottombarEvent(true));
                } else if (dy <= -5) {// 手指向下滚动
                    EventBus.getDefault().post(new HideBottombarEvent(false));
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        imageSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                start = new Random().nextInt(500);
                loadData();
            }
        });
    }

    private void loadData() {
        if (mImgsImp == null) {
            mImgsImp = new ImgsImp();
        }

        showRefresh();

        Call<ImgsCall> imgsCall = mImgsImp.getImages(mType, start, len);
        Call<ImgsCall> clone = imgsCall.clone();
        clone.enqueue(new Callback<ImgsCall>() {
            @Override
            public void onResponse(Call<ImgsCall> call, Response<ImgsCall> response) {
                if (response.isSuccessful()) {
                    ImgsCall body = response.body();
                    if (body == null) return;
                    List<ImageInfo> all_items = body.getAll_items();
                    if (all_items != null && all_items.size() > 0) {
                        if (isRefresh) {
                            mImagesListApapter.setNewData(all_items);
                            isRefresh = false;
                        } else {
                            mImagesListApapter.addData(all_items);
                        }
                        start += len;
                        mImagesListApapter.loadMoreComplete();
                    }
                }

                closeRefresh();
            }

            @Override
            public void onFailure(Call<ImgsCall> call, Throwable t) {
                showToast(R.string.net_error);
                if (t != null) {
                    CrashReport.postCatchedException(t);
                }
                closeRefresh();
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
