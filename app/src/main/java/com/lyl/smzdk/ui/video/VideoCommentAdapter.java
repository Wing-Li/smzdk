package com.lyl.smzdk.ui.video;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.video.XgComment;
import com.lyl.smzdk.ui.main.essay.DetailCommentReplyActivity;
import com.lyl.smzdk.ui.main.news.list.MyBaseViewHolder;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.MyUtils;

/**
 * 视频评论适配器
 * by lyl on 2017/5/23.
 */
public class VideoCommentAdapter extends BaseQuickAdapter<XgComment.DataBean.CommentsBean, MyBaseViewHolder>{


    public VideoCommentAdapter() {
        super(R.layout.item_eassay_detail_comment);
    }

    @Override
    protected void convert(MyBaseViewHolder holder, final XgComment.DataBean.CommentsBean data) {

        ImgUtils.loadCircle(mContext, data.getUser().getAvatar_url(), (ImageView) holder.getView(R.id.item_comment_icon));

        holder.setText(R.id.item_comment_name, data.getUser().getName());

        holder.setText(R.id.item_comment_time, MyUtils.getDate(data.getCreate_time()));

        ((ImageView)holder.getView(R.id.item_comment_thumb_up_img)).setColorFilter(Color.GRAY);

        holder.setText(R.id.item_comment_up_num, String.valueOf(data.getDigg_count()));

        holder.setText(R.id.item_comment_content, data.getText());


        if (data.getReply_count() > 0) {
            holder.setVisible(R.id.item_comment_hot_comment, true);
            holder.setText(R.id.item_comment_hot_comment, mContext.getString(R.string.text_show_num_comment, (int) data.getReply_count()));

            holder.getView(R.id.item_comment_hot_comment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailCommentReplyActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString(Constans.EASSAY_DETAIL_MGROUP_ID, String.valueOf(data.getId()));
                    bundle.putString(Constans.EASSAY_DETAIL_NAME, String.valueOf(data.getUser().getName()));
                    bundle.putString(Constans.EASSAY_DETAIL_ICON, String.valueOf( data.getUser().getAvatar_url()));
                    bundle.putString(Constans.EASSAY_DETAIL_TIME, String.valueOf(data.getCreate_time()));
                    bundle.putString(Constans.EASSAY_DETAIL_CONTENT, String.valueOf(data.getText()));
                    bundle.putString(Constans.EASSAY_DETAIL_COMMENT_ALL_NUM, String.valueOf(data.getDigg_count()));

                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.setVisible(R.id.item_comment_hot_comment, false);
        }
    }
}
