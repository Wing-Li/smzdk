package com.lyl.smzdk.ui.shop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.ShopInfo;
import com.lyl.smzdk.ui.BaseFragment;
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
        shopListview.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getHolder(), 10)));
        shopListview.setAdapter(mShopListApapter);
        shopListview.addOnScrollListener(mOnScrollHideBottombarListener);
    }

    private void loadData() {
        ShopInfo info;
        for (int i = 0; i < 2; i++) {
            info = new ShopInfo();
            info.setThumbs("http://jzvd-pic.nathen.cn/jzvd-pic/1d935cc5-a1e7-4779-bdfa-20fd7a60724c.jpg");
            info.setTitle("饺子这样不好");
            info.setSalePrice("12.35");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/b20397f90f6edf4bf512ff3e856213e7fb2549641a829d-ibSYFb_fw658");
            info.setTitle("家是个阳光四溢的地方");
            info.setSalePrice("125.35");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/196c517939335349688ac32ddfeb67863127eb9c34bdd0-0O68TZ_fw658");
            info.setTitle("来到你们的小家，绿植、多肉、鱼类、螃蟹、鸟、fighting，你们总能把生活的环境打");
            info.setSalePrice("99.99");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/a304bd1a5b098ff8c02d3d68a2a51b5ff98c88af3faccd-5FMjo0_fw658");
            info.setTitle("污渍的搜索结果");
            info.setSalePrice("88.35");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/188b0b050d2a59494b65d9a056de8a38a5f174d04af8f-zht301_fw658");
            info.setTitle("#背景素材#");
            info.setSalePrice("23.00");
            mShopListApapter.addData(info);
            info = new ShopInfo();
            info.setThumbs("http://img.hb.aicdn.com/0a0601b31c88790a4e76461f676911e77f4f160934f86-L0NQul_fw658");
            info.setTitle("斯瓦蒂佛斯瀑布,冰岛");
            info.setSalePrice("45.00");
            mShopListApapter.addData(info);
        }
        mShopListApapter.loadMoreComplete();
    }


}
