package com.lyl.smzdk.ui.main.news.list;

import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.network.imp.news.DzImp;
import com.lyl.smzdk.network.imp.news.WxImp;
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
public class ListPresenter implements ListContract.Presenter {

    private ListContract.View mView;

    private int page;

    public ListPresenter(ListContract.View view) {
        mView = view;
    }

    @Override
    public void reLoadData(String channelType, String type) {
        page = 0;
        loadData(channelType, type);
    }

    @Override
    public void loadData(final String channel, final String type) {
        Observable.create(new ObservableOnSubscribe<List<NewInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NewInfo>> ob) throws Exception {
                List<NewInfo> newInfoList = new ArrayList<>();
                switch (channel) {
                    case Constans.NEWS_TYPE_WEIXIN: { // 微信精选
                        WxImp wxImp = new WxImp();
                        newInfoList = wxImp.getWxList(type, page);
                        break;
                    }
                    case Constans.NEWS_TYPE_ZHIHU: { // 知乎精选
                        ZhImp zhImp = new ZhImp();
                        newInfoList = zhImp.getZhList(type, page);
                        break;
                    }
                    case Constans.NEWS_TYPE_DUZHE: { // 读者精选
                        DzImp dzImp = new DzImp();
                        newInfoList = dzImp.getInfo(type, page);
                        break;
                    }
                    default:
                        return;
                }

                ob.onNext(newInfoList);
            }
        })//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<List<NewInfo>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(List<NewInfo> newInfos) {
                        page++;
                        // 设置数据
                        mView.setData(newInfos);
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
