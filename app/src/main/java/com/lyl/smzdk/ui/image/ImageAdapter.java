package com.lyl.smzdk.ui.image;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.lyl.smzdk.network.entity.images.ImageInfo;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.LogUtils;

import java.util.List;

/**
 * Created by lyl on 2017/5/12.
 */

public class ImageAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageInfo> mData;

    public ImageAdapter(Context context, List<ImageInfo> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData != null && mData.size() >= 0 ? mData.size() : 0;
    }

    /**
     * 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageInfo bean = mData.get(position);
        PhotoView photoView = new PhotoView(mContext);
        photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
        photoView.setAdjustViewBounds(true);

        ImgUtils.load(mContext, bean.getPic_url(), photoView);

        LogUtils.d("多图显示", "pos:" + position + bean.getPic_url());

        container.addView(photoView);
        return photoView;
    }
}
