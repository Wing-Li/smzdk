package com.lyl.smzdk.ui.video;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.video.NhCommentReply;
import com.lyl.smzdk.network.entity.video.NhComments;
import com.lyl.smzdk.network.imp.news.NhImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.MyUtils;
import com.lyl.smzdk.view.ActionBar;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCommentReplyActivity extends BaseActivity {

    @BindView(R.id.comment_reply_icon)
    ImageView commentReplyIcon;
    @BindView(R.id.comment_reply_name)
    TextView commentReplyName;
    @BindView(R.id.comment_reply_time)
    TextView commentReplyTime;
    @BindView(R.id.comment_reply_content)
    TextView commentReplyContent;
    @BindView(R.id.comment_reply_recycler)
    RecyclerView commentReplyRecycler;
    @BindView(R.id.comment_reply_all_count)
    TextView commentReplyAllCount;
    @BindView(R.id.actionbar)
    ActionBar actionbar;

    private String mGroupId;
    private String mUserName;
    private String mUserIcon;
    private String mTime;
    private String mContent;
    private String mCommentDiggNum;
    private String mCommentHotNum;

    private EassayDetailCommentAdapter mAdapter;
    private List<NhComments.DataBean.CommentsBean> mCommentsBeenList;
    private NhImp mNeihanImp;
    private int page = 0;
    private boolean hasMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monment_detail_comment_reply);
        ButterKnife.bind(this);

        getParameter();
        initView();
        initData();
    }

    private void initData() {
        mNeihanImp = new NhImp(this);
        mCommentsBeenList = new ArrayList<>();

        page = 0;
        hasMore = true;
        getData();
    }

    private void getData() {
        if (!hasMore) {
            showToast(R.string.not_more);
            return;
        }

        Call<NhCommentReply> nhCommentReply = mNeihanImp.getNhCommentReply(mGroupId, page);
        Call<NhCommentReply> clone = nhCommentReply.clone();
        clone.enqueue(new Callback<NhCommentReply>() {
            @Override
            public void onResponse(Call<NhCommentReply> call, Response<NhCommentReply> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NhCommentReply body = response.body();
                    if ("success".equals(body.getMessage())) {
                        page++;
                        hasMore = body.getData().isHas_more();

                        commentReplyAllCount.setText("(" + body.getData().getTotal_count() + ")");
                        List<NhCommentReply.DataBeanX.DataBean> data = body.getData().getData();

                        NhComments.DataBean.CommentsBean commentsBean;
                        for (NhCommentReply.DataBeanX.DataBean d : data) {
                            commentsBean = new NhComments.DataBean.CommentsBean();
                            commentsBean.setAvatar_url(d.getUser().getAvatar_url());
                            commentsBean.setUser_name(d.getUser().getName());
                            commentsBean.setCreate_time(d.getCreate_time());
                            commentsBean.setDigg_count(d.getDigg_count());
                            commentsBean.setText(d.getText());
                            mCommentsBeenList.add(commentsBean);
                        }
                        mAdapter.setData(mCommentsBeenList);
                    }
                }
            }

            @Override
            public void onFailure(Call<NhCommentReply> call, Throwable t) {
                showToast(R.string.net_error);
                if (t != null) {
                    CrashReport.postCatchedException(t);
                }
            }
        });

    }

    private void initView() {
        actionbar.setModelBack(R.string.comment, mActivity);
        actionbar.setTitleColor(R.color.black);
        actionbar.mImgLeft.setImageResource(R.drawable.ic_arrow_back_black_24dp);

        ImgUtils.loadRound(mContext, mUserIcon, commentReplyIcon);

        commentReplyName.setText(mUserName);

        commentReplyTime.setText(MyUtils.getDate(Long.parseLong(mTime)));

        commentReplyContent.setText(mContent);

        commentReplyRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new EassayDetailCommentAdapter(mContext, mCommentsBeenList, EassayDetailCommentAdapter
                .COMMENT_TYPE_ALL);
        commentReplyRecycler.setAdapter(mAdapter);

//        commentReplyRecycler.addOnScrollListener(new OnRecycleViewScrollListener() {
//            @Override
//            public void onLoadMore() {
//                if (hasMore) {
//                    getData();
//                }
//            }
//        });
    }

    private void getParameter() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            showToast(getString(R.string.parameter_error));
            finish();
            return;
        }

        mGroupId = bundle.getString(Constans.EASSAY_DETAIL_MGROUP_ID);
        mUserName = bundle.getString(Constans.EASSAY_DETAIL_NAME);
        mUserIcon = bundle.getString(Constans.EASSAY_DETAIL_ICON);
        mTime = bundle.getString(Constans.EASSAY_DETAIL_TIME);
        mContent = bundle.getString(Constans.EASSAY_DETAIL_CONTENT);
        mCommentDiggNum = bundle.getString(Constans.EASSAY_DETAIL_COMMENT_ALL_NUM);
    }
}
