package com.lyl.smzdk.greendao.imp;

import com.lyl.smzdk.MyApp;
import com.lyl.smzdk.greendao.gen.HistoryEntity;
import com.lyl.smzdk.greendao.gen.HistoryEntityDao;
import com.lyl.smzdk.utils.LogUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 历史记录操作
 * Author: lyl
 * Date Created : 2017/12/14.
 */
public class HistoryImp {

    /**
     * 将一条数据保存进历史记录
     *
     * @param title
     * @param url
     */
    public static void saveHistory(final String title, final String url) {
        String date = String.valueOf(System.currentTimeMillis());
        HistoryEntity entity = new HistoryEntity(title, url, date);
        MyApp.mDaoSession.insert(entity);
    }

    /**
     * 某个文章是否已经看过
     *
     * @param title
     * @param url
     * @return
     */
    public static boolean isHistoryExist(String title, String url) {
        try {
            QueryBuilder qb = MyApp.mDaoSession.queryBuilder(HistoryEntity.class);
            qb.where(HistoryEntityDao.Properties.Title.eq(title))//
                    .where(HistoryEntityDao.Properties.Url.eq(url));
            long count = qb.count();
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogUtils.e("历史记录查询错误：" + e.getLocalizedMessage());
            return false;
        }
    }

    public static List<HistoryEntity> getHistoryList() {
        QueryBuilder qb = MyApp.mDaoSession.queryBuilder(HistoryEntity.class);
        return qb.list();
    }

}
