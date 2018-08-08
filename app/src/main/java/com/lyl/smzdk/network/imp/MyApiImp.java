package com.lyl.smzdk.network.imp;

import com.lyl.smzdk.network.entity.myapi.BaseCallBack;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: lyl
 * Date Created : 2018/8/8.
 */
public class MyApiImp<T> {

    /**
     * 请求 api 的基类方法
     * <p>
     * 成功：返回实体类
     * 失败：返回 code 和 错误提示
     *
     * @param observable      Observable 请求
     * @param netWorkCallBack 回调接口
     */
    public void request(Observable<BaseCallBack<T>> observable, final NetWorkCallBack<T> netWorkCallBack) {
        observable.subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<BaseCallBack<T>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseCallBack<T> userBaseCallBack) {
                        if (userBaseCallBack != null) {
                            if (200 == userBaseCallBack.getCode()) {
                                netWorkCallBack.onSuccess(userBaseCallBack.getData());
                            } else {
                                netWorkCallBack.onFail(userBaseCallBack.getCode(), userBaseCallBack.getMsg());
                            }
                        } else {
                            netWorkCallBack.onFail(500, "请求失败");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        netWorkCallBack.onFail(500, throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface NetWorkCallBack<T> {
        void onSuccess(T obj);

        void onFail(int code, String msg);
    }

}
