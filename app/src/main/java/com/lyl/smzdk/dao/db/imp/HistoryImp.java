package com.lyl.smzdk.dao.db.imp;

import android.content.Context;
import android.text.TextUtils;

import com.lyl.smzdk.MyApp;
import com.lyl.smzdk.dao.db.entity.HistoryEntity;
import com.lyl.smzdk.dao.db.entity.HistoryEntity_;
import com.lyl.smzdk.utils.LogUtils;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;

/**
 * 历史记录操作
 * Author: lyl
 * Date Created : 2017/12/14.
 */
public class HistoryImp {

    private Box<HistoryEntity> mHistoryEntityBox;

    public HistoryImp(Context context) {
        MyApp mMyApp = (MyApp) context.getApplicationContext();
        mHistoryEntityBox = mMyApp.getBoxStore().boxFor(HistoryEntity.class);
    }

    /**
     * 将一条数据保存进历史记录
     *
     * @param title
     * @param url
     */
    public void saveHistory(final String title, final String url) {
        String date = String.valueOf(System.currentTimeMillis());
        HistoryEntity entity = new HistoryEntity(title, url, date);
        mHistoryEntityBox.put(entity);
    }

    /**
     * 某个文章是否已经看过
     *
     * @param title
     * @param url
     *
     * @return
     */
    public boolean isHistoryExist(String title, String url) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(url))
            return false;

        try {
            Query<HistoryEntity> build = mHistoryEntityBox.query()//
                    .equal(HistoryEntity_.title, title)//
                    .and()//
                    .equal(HistoryEntity_.url, url)//
                    .build();

            long count = build.count();
            return count > 0;
        } catch (Exception e) {
            LogUtils.e("历史记录查询错误：" + e.getLocalizedMessage());
            return false;
        }
    }

    public List<HistoryEntity> getHistoryList() {
        return mHistoryEntityBox.getAll();
    }

}
