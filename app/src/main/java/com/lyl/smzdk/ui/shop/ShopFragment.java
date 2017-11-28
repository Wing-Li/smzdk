package com.lyl.smzdk.ui.shop;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.ShopInfo;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.web.Html5Activity;
import com.lyl.smzdk.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopFragment extends BaseFragment {


    @BindView(R.id.shop_listview)
    RecyclerView shopListview;

    private ShopListApapter mShopListApapter;
    private List<ShopInfo> mShopInfoList;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_shop;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar.setModelOnlyTitle(R.string.shop_title);

        setListView();
        loadData();
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.shop_primary);
    }

    private void setListView() {
        mShopInfoList = new ArrayList<>();

        mShopListApapter = new ShopListApapter(R.layout.item_shop_list, mShopInfoList);
        mShopListApapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mShopListApapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, shopListview);

        shopListview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        shopListview.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getHolder(), 4)));
        shopListview.setAdapter(mShopListApapter);
        shopListview.addOnScrollListener(mOnScrollHideBottombarListener);

        mShopListApapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ShopInfo info = (ShopInfo) baseQuickAdapter.getItem(i);
                if (info == null) {
                    showToast(getString(R.string.data_error));
                    return;
                }
                View titleView = view.findViewById(R.id.item_shop_title);

                Intent intent = new Intent(getHolder(), Html5Activity.class);
                intent.putExtra(Constans.I_WEB_TITLE, info.getTitle());
                intent.putExtra(Constans.I_WEB_URL, info.getUrl());
                intent.putExtra(Constans.I_CHANNEL_TYPE_TYPE, Constans.NEWS_TYPE_WEIXIN);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create
                            (titleView, "content_title"));
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
    }

    private void loadData() {
        ShopInfo info;
        for (int i = 0; i < 2; i++) {
            info = new ShopInfo();
            info.setThumbs("http://jzvd-pic.nathen.cn/jzvd-pic/1d935cc5-a1e7-4779-bdfa-20fd7a60724c.jpg");
            info.setTitle("饺子这样不好");
            info.setUrl("http://www.jianshu.com/p/a43daa1e3d6e");
            info.setOriginalPrice("17.35");
            info.setSalePrice("5");
            info.setPrice("12.35");
            info.setSalesVolume("65");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/b20397f90f6edf4bf512ff3e856213e7fb2549641a829d-ibSYFb_fw658");
            info.setTitle("家是个阳光四溢的地方");
            info.setUrl("http://www.jianshu.com/p/a43daa1e3d6e");
            info.setOriginalPrice("125.35");
            info.setSalePrice("20");
            info.setPrice("105.35");
            info.setSalesVolume("155");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/196c517939335349688ac32ddfeb67863127eb9c34bdd0-0O68TZ_fw658");
            info.setTitle("来到你们的小家，绿植、多肉、鱼类、螃蟹、鸟、fighting，你们总能把生活的环境打");
            info.setUrl("http://www.jianshu.com/p/a43daa1e3d6e");
            info.setOriginalPrice("1700.35");
            info.setSalePrice("700");
            info.setPrice("100.35");
            info.setSalesVolume("5648");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/a304bd1a5b098ff8c02d3d68a2a51b5ff98c88af3faccd-5FMjo0_fw658");
            info.setTitle("污渍的搜索结果");
            info.setUrl("http://www.jianshu.com/p/a43daa1e3d6e");
            info.setOriginalPrice("399");
            info.setSalePrice("100");
            info.setPrice("299");
            info.setSalesVolume("465");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/188b0b050d2a59494b65d9a056de8a38a5f174d04af8f-zht301_fw658");
            info.setTitle("#背景素材#");
            info.setUrl("http://www.jianshu.com/p/a43daa1e3d6e");
            info.setOriginalPrice("999");
            info.setSalePrice("500");
            info.setPrice("499");
            info.setSalesVolume("2");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/0a0601b31c88790a4e76461f676911e77f4f160934f86-L0NQul_fw658");
            info.setTitle("斯瓦蒂佛斯瀑布,冰岛");
            info.setUrl("http://www.jianshu.com/p/a43daa1e3d6e");
            info.setOriginalPrice("1099");
            info.setSalePrice("100");
            info.setPrice("999");
            info.setSalesVolume("999");
            mShopListApapter.addData(info);
        }
        mShopListApapter.loadMoreComplete();
    }


}
