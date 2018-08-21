package com.lyl.smzdk.ui.main.images;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.images.ImageInfo;
import com.lyl.smzdk.network.imp.news.MvtImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.ui.image.SpecialImageActivity;
import com.lyl.smzdk.utils.MyUtils;
import com.lyl.smzdk.utils.SnackbarUtils;
import com.lyl.smzdk.view.layoutmanager.LinearLayoutManagerWrapper;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SummaryImagesActivity extends BaseActivity {

    public static final String IMAGE_LIST_TYPE = "image_list_type";
    public static final String IMAGE_LIST_TITLE = "image_list_title";

    @BindView(R.id.image_listview)
    RecyclerView imageListview;
    @BindView(R.id.image_swiperefresh)
    SwipeRefreshLayout imageSwiperefresh;

    private MvtImp mImgsImp;
    private String mTitle;
    private String mType;

    private List<ImageInfo> mInfoList;
    private ImagesListApapter mImagesListApapter;
    private boolean isRefresh;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_images_list);

        translucentStatusAndNavigation();

        ButterKnife.bind(this);

        Intent intent = getIntent();
        mType = intent.getStringExtra(IMAGE_LIST_TYPE);
        mTitle = intent.getStringExtra(IMAGE_LIST_TITLE);

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

                Intent intent = new Intent(mContext, SpecialImageActivity.class);
                intent.putExtra(Constans.SPECIAL_IMAGE_URL, imageInfo.getPic_url());
                if (imageInfo.getHeight() > 2500) {
                    intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_LONG);
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    View imagePlayView = view.findViewById(R.id.item_images_img);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, Pair.create(imagePlayView, "image"));
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        imageListview.setLayoutManager(new LinearLayoutManagerWrapper(mContext));
        imageListview.setAdapter(mImagesListApapter);
        imageListview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // 滚动到底部
                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView
                        .computeVerticalScrollRange()) {

                    // 如果没登陆 或者 当前是普通会员
                    if (!MyUtils.isLogin(mContext) || MyUtils.isVipNormal(mContext)){
                        SnackbarUtils.showRechargeVipDialog(mContext, recyclerView);
                    }

                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        imageSwiperefresh.clearFocus();
        imageSwiperefresh.setEnabled(false);
    }

    private void loadData() {
        if (mImgsImp == null) {
            mImgsImp = new MvtImp();
        }

        showRefresh();
        Observable.create(new ObservableOnSubscribe<List<ImageInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ImageInfo>> observableEmitter) throws Exception {

                List<ImageInfo> details = mImgsImp.getDetails(mType);
                observableEmitter.onNext(details);
            }
        })//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<List<ImageInfo>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(List<ImageInfo> imageInfos) {
                        if (imageInfos != null && imageInfos.size() > 0) {

                            // 没有登录
                            if (!MyUtils.isLogin(mContext)) {
                                removeFrom(imageInfos, 3);

                            } else if (MyUtils.isVipNormal(mContext)) {// 普通会员
                                removeFrom(imageInfos, 7);

                            } else if (MyUtils.isVipRecharge(mContext)) {//充值会员
                                // 全部显示
                            } else {

                                return;
                            }

                            if (isRefresh) {
                                mImagesListApapter.setNewData(imageInfos);
                                isRefresh = false;
                            } else {
                                mImagesListApapter.addData(imageInfos);
                            }
                            mImagesListApapter.loadMoreEnd();
                        }
                        closeRefresh();
                    }

                    @Override
                    public void onError(final Throwable throwable) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast(R.string.net_error);
                                if (throwable != null) {
                                    CrashReport.postCatchedException(throwable);
                                }
                                closeRefresh();
                            }
                        });
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 从 pos 之后全部删除
     */
    private void removeFrom(List<ImageInfo> list, int pos) {
        List<ImageInfo> sublist = list.subList(pos, list.size());
        list.removeAll(sublist);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
