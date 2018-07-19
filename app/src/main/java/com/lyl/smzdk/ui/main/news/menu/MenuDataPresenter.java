package com.lyl.smzdk.ui.main.news.menu;

import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.network.imp.news.DzImp;
import com.lyl.smzdk.network.imp.news.LzsImp;
import com.lyl.smzdk.network.imp.news.ShfImp;
import com.lyl.smzdk.network.imp.news.WxImp;
import com.lyl.smzdk.network.imp.news.XdImp;
import com.lyl.smzdk.network.imp.news.ZhImp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: lyl
 * Date Created : 2017/11/20.
 */
public class MenuDataPresenter implements MenuContract.Presenter {

    private MenuContract.View mView;


    public MenuDataPresenter(MenuContract.View view) {
        mView = view;
    }

    @Override
    public void initMenuData(final String type) {
        Observable.create(new ObservableOnSubscribe<List<NewMenu>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NewMenu>> ob) throws Exception {
                // 在子线程中请求数据
                List<NewMenu> mNewMenuList = new ArrayList<>();

                switch (type) {
                    case Constans.NEWS_TYPE_WEIXIN: { // 微信精选
                        WxImp wxImp = new WxImp();
                        mNewMenuList = wxImp.getWxMenu();
                        break;
                    }
                    case Constans.NEWS_TYPE_ZHIHU:{ // 知乎精选
                        ZhImp zhImp = new ZhImp();
                        mNewMenuList = zhImp.getZhMenu();
                        break;
                    }
                    case Constans.NEWS_TYPE_DUZHE:{ // 读者精选
                        DzImp dzImp = new DzImp();
                        mNewMenuList = dzImp.getDzMenu();
                        break;
                    }
                    case Constans.NEWS_TYPE_XIANDU:{ // 闲读
                        XdImp xd = new XdImp();
                        mNewMenuList = xd.getXdMenu();
                        break;
                    }
                    case Constans.NEWS_TYPE_XIUXIAN:{ // 内涵精选
                        ShfImp imp = new ShfImp();
                        mNewMenuList = imp.getShfMenu();
                        break;
                    }
                    case Constans.NEWS_TYPE_LENGZHISHI:{ // 冷知识
                        LzsImp lzs = new LzsImp();
                        mNewMenuList = lzs.getLzsMenu();
                        break;
                    }
                }

                // 将数据传递下去
                ob.onNext(mNewMenuList);
            }
        })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribeOn(Schedulers.io())//
                .subscribe(new Observer<List<NewMenu>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(List<NewMenu> newMenus) {
                        // 初始化 目录的数据
                        mView.setMenuTab(newMenus);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
