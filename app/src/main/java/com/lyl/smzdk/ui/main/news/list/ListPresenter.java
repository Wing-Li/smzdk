package com.lyl.smzdk.ui.main.news.list;

import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.LzsInfo;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.network.imp.news.DzImp;
import com.lyl.smzdk.network.imp.news.LzsImp;
import com.lyl.smzdk.network.imp.news.ShfImp;
import com.lyl.smzdk.network.imp.news.WabaImp;
import com.lyl.smzdk.network.imp.news.WxImp;
import com.lyl.smzdk.network.imp.news.XdImp;
import com.lyl.smzdk.network.imp.news.ZhImp;
import com.lyl.smzdk.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            public void subscribe(final ObservableEmitter<List<NewInfo>> ob) throws Exception {
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

                    case Constans.NEWS_TYPE_XIANDU: { // 闲读
                        XdImp xd = new XdImp();
                        newInfoList = xd.getInfo(type, page);
                        break;
                    }

                    case Constans.NEWS_TYPE_XIUXIAN:{ // 内涵精选
                        ShfImp imp = new ShfImp();
                        newInfoList = imp.getInfo(type, page);
                        break;
                    }

                    case Constans.NEWS_TYPE_LENGZHISHI: { // 冷知识
                        LzsImp lzs = new LzsImp();
                        Call<List<LzsInfo>> infoCall = lzs.getInfo(type, page);
                        Call<List<LzsInfo>> clone = infoCall.clone();

                        clone.enqueue(new Callback<List<LzsInfo>>() {
                            @Override
                            public void onResponse(Call<List<LzsInfo>> call, Response<List<LzsInfo>> response) {
                                if (response.isSuccessful()) {
                                    List<LzsInfo> body = response.body();
                                    if (body != null) {
                                        List<NewInfo> newInfoList = new ArrayList<>();
                                        NewInfo newInfo;

                                        // 将从百度百科获取的数据，转化为自己的类
                                        for (LzsInfo info : body) {
                                            newInfo = new NewInfo();
                                            newInfo.setTitle(info.getTitle());
                                            newInfo.setUrl(info.getLink());
                                            newInfo.setTime(DateUtils.translateDate((long) info.getPublishTime(), System.currentTimeMillis()));
                                            newInfo.setAuthor(info.getAuthor());
                                            newInfo.setIntroduce(info.getDesc());
                                            newInfo.setImage(info.getPic());
                                            newInfo.setReadNum(String.valueOf(info.getPv()));

                                            newInfoList.add(newInfo);
                                        }

                                        ob.onNext(newInfoList);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<LzsInfo>> call, Throwable t) {

                            }
                        });
                        return;
                    }

                    case Constans.NEWS_TYPE_WABA:{ // 挖吧
                        WabaImp imp = new WabaImp();
                        newInfoList = imp.getInfo(type, page);
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
