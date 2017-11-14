package com.lyl.smzdk.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.VideoInfo;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.view.LinearLayoutManagerWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoListFragment extends BaseFragment {

    private static final String VIDEO_LIST_TYPE = "video_list_type";
    private static final String VIDEO_LIST_TITLE = "video_list_title";

    @BindView(R.id.video_listview)
    RecyclerView videoListview;

    private List<VideoInfo> mInfoList;
    private VideoListAdapter mVideoListAdapter;

    public VideoListFragment() {
    }

    public static VideoListFragment newInstance(String type, String title) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putString(VIDEO_LIST_TYPE, type);
        args.putString(VIDEO_LIST_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_videoinfo_list;
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
        mVideoListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, videoListview);

        videoListview.setLayoutManager(new LinearLayoutManagerWrapper(getHolder()));
        videoListview.setAdapter(mVideoListAdapter);
    }

    private void loadData() {
        VideoInfo info;
        for (int i = 0; i < 10; i++) {
            info = new VideoInfo();
            info.setThumbs("http://jzvd-pic.nathen.cn/jzvd-pic/1d935cc5-a1e7-4779-bdfa-20fd7a60724c.jpg");
            info.setTitle("饺子这样不好");
            info.setUrl("http://jzvd.nathen.cn/b201be3093814908bf987320361c5a73/2f6d913ea25941ffa78cc53a59025383" +
                    "-5287d2089db37e62345123a1be272f8b.mp4");
            mVideoListAdapter.addData(info);
        }
        mVideoListAdapter.loadMoreComplete();
    }
}
