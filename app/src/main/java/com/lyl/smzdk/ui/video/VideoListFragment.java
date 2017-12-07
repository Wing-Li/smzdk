package com.lyl.smzdk.ui.video;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.HideBottombarEvent;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.video.VideoInfo;
import com.lyl.smzdk.network.entity.video.XgInfo;
import com.lyl.smzdk.network.imp.video.XgImp;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.view.LinearLayoutManagerWrapper;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListFragment extends BaseFragment {

    private static final String VIDEO_LIST_TYPE = "video_list_type";
    private static final String VIDEO_LIST_TITLE = "video_list_title";

    @BindView(R.id.video_listview)
    RecyclerView videoListview;

    private XgImp mXgImp;
    private String mTitle;
    private String mType;
    private int max_behot_time;

    private List<VideoInfo> mInfoList;
    private VideoListAdapter mVideoListAdapter;

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
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_video_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListView();
        loadData();
    }

    private void setListView() {
        mInfoList = new ArrayList<>();
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
        videoListview.setAdapter(mVideoListAdapter);
        videoListview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    }

    private void goToVideoPlayActivity(VideoInfo info, View videoPlayView) {
        Intent intent = new Intent(getHolder(), VideoPlayerActivity.class);
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

    private void loadData() {
        if (mXgImp == null) {
            mXgImp = new XgImp();
        }

        Call<XgInfo> xgList = mXgImp.getXgList(mType,max_behot_time);
        Call<XgInfo> clone = xgList.clone();
        clone.enqueue(new Callback<XgInfo>() {
            @Override
            public void onResponse(Call<XgInfo> call, Response<XgInfo> response) {
                if (response.isSuccessful()) {
                    XgInfo body = response.body();
                    if (body == null) return;
                    max_behot_time = body.getNext().getMax_behot_time();

                    List<XgInfo.DataBean> data = body.getData();
                    if (data != null && data.size() > 0) {
                        VideoInfo info;
                        for (XgInfo.DataBean bean : data) {
                            info = new VideoInfo();
                            info.setId(bean.getVideo_id());
                            info.setTitle(bean.getTitle());
                            info.setImage(bean.getImage_url());
                            info.setVideoDuration(bean.getVideo_duration_str());
                            info.setPlayCount(String.valueOf(bean.getVideo_play_count()));
                            info.setGroup_id(bean.getGroup_id());
                            info.setSource_url(bean.getSource_url());

                            info.setAuthor(bean.getSource());
                            info.setAuthorUrl(Network.URL_XG + bean.getMedia_url());
                            info.setAuthorIcon(bean.getMedia_avatar_url());

                            mVideoListAdapter.addData(info);
                        }

                        mVideoListAdapter.loadMoreComplete();
                    }
                }

            }

            @Override
            public void onFailure(Call<XgInfo> call, Throwable t) {
                showToast(R.string.net_error);
                if (t != null) {
                    CrashReport.postCatchedException(t);
                }
            }
        });
    }
}
