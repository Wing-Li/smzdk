package com.lyl.smzdk.ui.main.essay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NhEassay;
import com.lyl.smzdk.ui.image.ImageActivity;
import com.lyl.smzdk.ui.image.SpecialImageActivity;
import com.lyl.smzdk.ui.main.news.list.MyBaseViewHolder;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.LogUtils;
import com.lyl.smzdk.utils.MyUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/24.
 */
public class NhEassayListAdapter extends BaseQuickAdapter<NhEassay.DataBeanX.DataBean, MyBaseViewHolder> {

    public static final int CONTENT_TYPE_ESSAY = 1;
    public static final int CONTENT_TYPE_IMAGE = 2;

    private String mContentType;
    private int mScreenWidth;

    public NhEassayListAdapter(@Nullable List<NhEassay.DataBeanX.DataBean> data, String contentType, int screenWidth) {
        super(data);

        mContentType = contentType;
        mScreenWidth = screenWidth;

        setMultiTypeDelegate(new MultiTypeDelegate<NhEassay.DataBeanX.DataBean>() {
            @Override
            protected int getItemType(NhEassay.DataBeanX.DataBean dataBean) {
                if (NhEassayListFragment.CONTENT_TYPE_ESSAY.equals(mContentType)) {
                    return CONTENT_TYPE_ESSAY;
                } else if (NhEassayListFragment.CONTENT_TYPE_IMAGE.equals(mContentType)) {
                    return CONTENT_TYPE_IMAGE;
                }

                return CONTENT_TYPE_ESSAY;
            }
        });

        getMultiTypeDelegate()//
                .registerItemType(CONTENT_TYPE_ESSAY, R.layout.item_eassay)//
                .registerItemType(CONTENT_TYPE_IMAGE, R.layout.item_eassay_image);
    }

    @Override
    protected void convert(MyBaseViewHolder holder, final NhEassay.DataBeanX.DataBean dataBean) {
        if ("5".equals(dataBean.getType())) { // 广告
            return;
        }
        final NhEassay.DataBeanX.DataBean.GroupBean group = dataBean.getGroup();
        if (group == null) return;
        NhEassay.DataBeanX.DataBean.GroupBean.UserBean user = group.getUser();

        if (user != null) {
            // 头像
            ImgUtils.loadCircle(mContext, user.getAvatar_url(), (ImageView) holder.getView(R.id.item_eassay_icon));

            // 名字
            holder.setText(R.id.item_eassay_name, user.getName());
        }
        // 时间
        holder.setText(R.id.item_eassay_time, MyUtils.getDate(dataBean.getGroup().getCreate_time()));
        // 内容
        holder.setText(R.id.item_eassay_content, dataBean.getGroup().getContent());

        switch (getItemViewType(holder.getLayoutPosition())) {
            case CONTENT_TYPE_ESSAY:// 段子
                View eassay_layout = holder.getView(R.id.item_eassay_layout);
                eassay_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goDetails(group, CONTENT_TYPE_ESSAY);
                    }
                });
                break;
            case CONTENT_TYPE_IMAGE:// 图片
                holder.setImageBitmap(R.id.item_image_content, null);
                if (group.getLarge_image_list() == null) {// 一张图片
                    holder.setVisible(R.id.item_single_image_layout, true);
                    holder.setVisible(R.id.item_image_list, false);

                    // 设置图片的大小
                    int width = Integer.parseInt(group.getMiddle_image().getWidth());
                    int height = Integer.parseInt(group.getMiddle_image().getHeight());
                    int tHeight = mScreenWidth * height / width;

                    boolean isLongImage = false;
                    holder.setVisible(R.id.item_long_image_text, false);
                    View imageContent = holder.getView(R.id.item_image_content);
                    ViewGroup.LayoutParams layoutParams = imageContent.getLayoutParams();
                    layoutParams.width = mScreenWidth;
                    if (tHeight > 1920) {
                        layoutParams.height = mScreenWidth / 16 * 9;
                        isLongImage = true;
                    } else {
                        layoutParams.height = tHeight;
                    }
                    imageContent.setLayoutParams(layoutParams);

                    // 加载图片
                    final List<NhEassay.DataBeanX.DataBean.GroupBean.LargeImageBean.UrlListBean> url_list = group
                            .getLarge_image().getUrl_list();
                    final String imgUrl = url_list.get(0).getUrl();
                    if (1 == group.getIs_gif()) {// 是gif
                        holder.setText(R.id.item_long_image_text, R.string.show_gif_image);
                        holder.setVisible(R.id.item_long_image_text, true);

                        String tmUrl = group.getMiddle_image().getUrl_list().get(0).getUrl();
                        ImgUtils.load(mContext, tmUrl, (ImageView) imageContent);
                        LogUtils.d("GIF显示的Url:", tmUrl);
                        // 跳转到 GIF 页面
                        RelativeLayout singleImg = holder.getView(R.id.item_single_image_layout);
                        singleImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(mContext, SpecialImageActivity.class);
                                intent.putExtra(Constans.SPECIAL_IMAGE_URL, imgUrl);
                                intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_GIF);
                                mContext.startActivity(intent);
                            }
                        });
                    } else {
                        if (isLongImage) {// 是长图
                            holder.setText(R.id.item_long_image_text, R.string.show_long_image);
                            holder.setVisible(R.id.item_long_image_text, true);

                            // 跳转到长图页面
                            RelativeLayout singleImg = holder.getView(R.id.item_single_image_layout);
                            singleImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(mContext, SpecialImageActivity.class);
                                    intent.putExtra(Constans.SPECIAL_IMAGE_URL, imgUrl);
                                    intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_LONG);
                                    mContext.startActivity(intent);
                                }
                            });
                        } else {// 普通图片
                            ImgUtils.load(mContext, imgUrl, (ImageView) holder.getView(R.id.item_image_content));
                            // 跳转到可放大缩小页面
                            RelativeLayout singleImg = holder.getView(R.id.item_single_image_layout);
                            singleImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(mContext, SpecialImageActivity.class);
                                    intent.putExtra(Constans.SPECIAL_IMAGE_URL, imgUrl);
                                    intent.putExtra(Constans.SPECIAL_IMAGE_TYPE, Constans.SPECIAL_IMAGE_NORMAL);
                                    mContext.startActivity(intent);
                                }
                            });
                        }
                    }
                } else { // 多张图片
                    holder.setVisible(R.id.item_single_image_layout, false);
                    holder.setVisible(R.id.item_image_list, true);

                    final List<NhEassay.DataBeanX.DataBean.GroupBean.ThumbImageListBean> thumb_image_list = group
                            .getThumb_image_list();

                    GridView imageGrid = holder.getView(R.id.item_image_list);
                    ViewGroup.LayoutParams layoutParams = imageGrid.getLayoutParams();
                    int imgSize = thumb_image_list.size();
                    if (imgSize == 2 || imgSize == 4) {
                        imageGrid.setNumColumns(2);
                        if (imgSize <= 2) {
                            layoutParams.height = mScreenWidth / 2 - 4;
                        } else {
                            layoutParams.height = mScreenWidth - 4;
                        }
                    } else {
                        imageGrid.setNumColumns(3);
                        if (imgSize <= 3) {
                            layoutParams.height = mScreenWidth / 3 - 4;
                        } else if (imgSize <= 6) {
                            layoutParams.height = mScreenWidth / 3 * 2 - 4;
                        } else {
                            layoutParams.height = mScreenWidth - 4;
                        }
                    }

                    imageGrid.setLayoutParams(layoutParams);

                    ImageGridAdapter imageGridAdapter = new ImageGridAdapter(mContext, thumb_image_list);
                    imageGrid.setAdapter(imageGridAdapter);

                    imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Intent intent = new Intent(mContext, ImageActivity.class);
                            intent.putExtra(Constans.IMAGE_LIST, (Serializable) group.getLarge_image_list());
                            intent.putExtra(Constans.IMAGE_LIST_POSTION, i);
                            mContext.startActivity(intent);
                        }
                    });
                }
                holder.getView(R.id.item_eassay_image_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goDetails(group, CONTENT_TYPE_IMAGE);
                    }
                });
                break;
        }
    }

    private void goDetails(NhEassay.DataBeanX.DataBean.GroupBean group, int conetntType) {

        Intent intent = new Intent(mContext, EassayDetailActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(Constans.EASSAY_DETAIL_MGROUP_ID, String.valueOf(group.getGroup_id()));
        bundle.putString(Constans.EASSAY_DETAIL_NAME, String.valueOf(group.getUser().getName()));
        bundle.putString(Constans.EASSAY_DETAIL_ICON, String.valueOf(group.getUser().getAvatar_url()));
        bundle.putString(Constans.EASSAY_DETAIL_TIME, String.valueOf(group.getCreate_time()));
        bundle.putString(Constans.EASSAY_DETAIL_CONTENT, String.valueOf(group.getContent()));
        bundle.putString(Constans.EASSAY_DETAIL_COMMENT_ALL_NUM, String.valueOf(group.getComment_count()));

        switch (conetntType) {
            case CONTENT_TYPE_ESSAY:// 段子
                bundle.putInt(Constans.EASSAY_DETAIL_CONTENT_TYPE, Constans.EASSAY_DETAIL_CONTENT_TEXT);
                break;
            case CONTENT_TYPE_IMAGE:// 图片
                if (group.getLarge_image_list() == null) {// 一张图片
                    int width = Integer.parseInt(group.getMiddle_image().getWidth());
                    int height = Integer.parseInt(group.getMiddle_image().getHeight());
                    int tHeight = mScreenWidth * height / width;

                    boolean isLongImage = false;
                    if (tHeight > 1920) {
                        isLongImage = true;
                    }

                    final List<NhEassay.DataBeanX.DataBean.GroupBean.LargeImageBean.UrlListBean> url_list = group
                            .getLarge_image().getUrl_list();
                    final String imgUrl = url_list.get(0).getUrl();

                    if (1 == group.getIs_gif()) {// 是gif
                        bundle.putString(Constans.SPECIAL_IMAGE_URL, group.getMiddle_image().getUrl_list().get(0)
                                .getUrl());
                        bundle.putInt(Constans.EASSAY_DETAIL_CONTENT_TYPE, Constans.EASSAY_DETAIL_CONTENT_GIF);
                    } else {
                        if (isLongImage) {// 是长图
                            bundle.putString(Constans.SPECIAL_IMAGE_URL, imgUrl);
                            bundle.putInt(Constans.EASSAY_DETAIL_CONTENT_TYPE, Constans.EASSAY_DETAIL_CONTENT_LONG);
                        } else {// 普通图
                            bundle.putString(Constans.SPECIAL_IMAGE_URL, imgUrl);
                            bundle.putInt(Constans.EASSAY_DETAIL_CONTENT_TYPE, Constans.EASSAY_DETAIL_CONTENT_NORMAL);
                        }
                    }
                } else { // 多张图
                    bundle.putSerializable(Constans.IMAGE_THUMB_LIST, (Serializable) group.getThumb_image_list());
                    bundle.putSerializable(Constans.IMAGE_LIST, (Serializable) group.getLarge_image_list());
                    bundle.putInt(Constans.EASSAY_DETAIL_CONTENT_TYPE, Constans.EASSAY_DETAIL_CONTENT_LIST);
                }

                break;
        }

        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
