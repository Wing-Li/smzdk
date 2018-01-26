package com.lyl.smzdk.ui.video;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.HideBottombarEvent;
import com.lyl.smzdk.network.entity.video.VideoInfo;
import com.lyl.smzdk.network.entity.video.XgInfo;
import com.lyl.smzdk.network.imp.video.XgImp;
import com.lyl.smzdk.view.LinearLayoutManagerWrapper;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListFragment extends NoPreloadFragment {

    private static final String VIDEO_LIST_TYPE = "video_list_type";
    private static final String VIDEO_LIST_TITLE = "video_list_title";

    @BindView(R.id.video_listview)
    RecyclerView videoListview;
    @BindView(R.id.video_swiperefresh)
    SwipeRefreshLayout videoSwiperefresh;


    private XgImp mXgImp;
    private String mTitle;
    private String mType;

    private List<VideoInfo> mInfoList;
    private VideoListAdapter mVideoListAdapter;
    private boolean isRefresh;
    private boolean isFirst;
    private long behot_time;

    public static VideoListFragment newInstance(String type, String title) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putString(VIDEO_LIST_TYPE, type);
        args.putString(VIDEO_LIST_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        mType = bundle.getString(VIDEO_LIST_TYPE);
        mTitle = bundle.getString(VIDEO_LIST_TITLE);

        mInfoList = new ArrayList<>();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_video_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isFirst = true;
        isRefresh = true;
        setListView();
    }

    private void setListView() {
        mVideoListAdapter = new VideoListAdapter(R.layout.item_video_list, mInfoList);
        mVideoListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mVideoListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, videoListview);

        mVideoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                VideoInfo videoInfo = (VideoInfo) baseQuickAdapter.getItem(i);
                View videoPlayView = view.findViewById(R.id.item_video_thm);
                goToVideoPlayActivity(videoInfo, videoPlayView);
            }
        });

        videoListview.setLayoutManager(new LinearLayoutManagerWrapper(getHolder()));
        videoListview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        videoListview.setAdapter(mVideoListAdapter);
        videoListview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy >= 10) {// 手指向上滚动
                    EventBus.getDefault().post(new HideBottombarEvent(true));
                } else if (dy <= -10) {// 手指向下滚动
                    EventBus.getDefault().post(new HideBottombarEvent(false));
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        videoSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                loadData();
            }
        });
    }

    private void goToVideoPlayActivity(VideoInfo info, View videoPlayView) {
        Intent intent = new Intent(getHolder(), VideoPlayerActivity.class);
        intent.putExtra(Constans.I_ID, info.getGroup_id());
        intent.putExtra(Constans.I_TITLE, info.getTitle());
        intent.putExtra(Constans.I_IMAGE, info.getImage());
        intent.putExtra(Constans.I_URL, info.getGroup_id());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create
                    (videoPlayView, "video_play"));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

    protected void loadData() {
        if (mXgImp == null) {
            mXgImp = new XgImp();
        }

        showRefresh();

        Call<XgInfo> xgList = mXgImp.getXgList(mType, behot_time, isFirst);
        Call<XgInfo> clone = xgList.clone();
        clone.enqueue(new Callback<XgInfo>() {
            @Override
            public void onResponse(Call<XgInfo> call, Response<XgInfo> response) {
                if (response.isSuccessful()) {
                    XgInfo body = response.body();
                    if (body == null) return;

                    List<XgInfo.DataBean> data = body.getData();
                    if (data != null && data.size() > 0) {
                        List<VideoInfo> videoInfos = new ArrayList<>();
                        VideoInfo info;
                        for (XgInfo.DataBean bean : data) {
                            info = new VideoInfo();
                            info.setId(bean.getVideo_id());
                            info.setTitle(bean.getTitle());
                            info.setImage(bean.getLarge_image_url());
                            info.setVideoDuration(bean.getVideo_duration());
                            info.setGroup_id(bean.getGroup_id());
                            info.setSource_url(bean.getSource_url());
                            info.setDatetime(bean.getDatetime());
                            info.setLaudNum(String.valueOf(bean.getDigg_count()));

                            XgInfo.DataBean.VideoDetailInfoBean video_detail_info = bean.getVideo_detail_info();
                            if (video_detail_info != null) {
                                String playCount = "0";
                                int watchCount = video_detail_info.getVideo_watch_count();
                                if (watchCount > 1000){
                                    playCount = (watchCount / 1000) +"k";
                                } else if (watchCount > 1000000){
                                    playCount = (watchCount / 1000000) +"M";
                                }
                                info.setPlayCount(playCount);
                            }

                            info.setAuthor(bean.getMedia_name());
                            XgInfo.DataBean.MediaInfoBean media_info = bean.getMedia_info();
                            if (media_info != null) {
                                info.setAuthorUrl(media_info.getAvatar_url());
                            }

                            videoInfos.add(info);

                            // 实际上，这里需要的是最后一个视频的时间，下一次请求是传过去，就知道哪个是最后一个了
                            // 为了方便，这里每次循环都赋值，到了最后一次，就会替换掉之前所有的。
                            behot_time = bean.getBehot_time();
                        }

                        if (mVideoListAdapter != null) {
                            if (isRefresh) {
                                mVideoListAdapter.setNewData(videoInfos);
                                isRefresh = false;
                            } else {
                                mVideoListAdapter.addData(videoInfos);
                            }

                            mVideoListAdapter.loadMoreComplete();
                        } else {
                            mInfoList = videoInfos;
                        }
                    }

                    closeRefresh();
                }

            }

            @Override
            public void onFailure(Call<XgInfo> call, Throwable t) {
                showToast(R.string.net_error);
                if (t != null) {
                    CrashReport.postCatchedException(t);
                }
                closeRefresh();
            }
        });

        if (isFirst) {
            isFirst = false;
        }
    }

    private void closeRefresh() {
        if (videoSwiperefresh != null && videoSwiperefresh.isRefreshing()) {
            videoSwiperefresh.setRefreshing(false);
        }
    }

    private void showRefresh() {
        if (videoSwiperefresh != null && !videoSwiperefresh.isRefreshing()) {
            videoSwiperefresh.setRefreshing(true);
        }
    }
}
