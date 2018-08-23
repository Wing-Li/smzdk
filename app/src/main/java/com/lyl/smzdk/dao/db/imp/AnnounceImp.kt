package com.lyl.smzdk.dao.db.imp

import android.content.Context

import com.lyl.smzdk.MyApp
import com.lyl.smzdk.dao.db.entity.AnnounceEntity
import com.lyl.smzdk.dao.db.entity.AnnounceEntity_
import com.lyl.smzdk.utils.LogUtils

import io.objectbox.Box

/**
 * 公告的记录
 * Author: lyl
 * Date Created : 2018/8/23.
 */
class AnnounceImp(context: Context) {

    private val mAnnounceEntityBox: Box<AnnounceEntity>

    init {
        val mMyApp = context.applicationContext as MyApp
        mAnnounceEntityBox = mMyApp.boxStore.boxFor(AnnounceEntity::class.java)
    }

    /**
     * 看过某个公告之后，保存进数据库
     *
     * @param announceId
     * @param title
     */
    fun save(announceId: Long, title: String) {
        val date = System.currentTimeMillis().toString()
        val entity = AnnounceEntity(announceId, title, date)
        mAnnounceEntityBox.put(entity)
    }

    /**
     * 某个公告是否看过
     */
    fun isExits(announceId: Long): Boolean {
        if (0L == announceId) {
            return false
        }

        try {
            val countQuery = mAnnounceEntityBox.query()//
                    .equal(AnnounceEntity_.announceId, announceId)//
                    .build();
            val count = countQuery.count()
            return count > 0
        } catch (e: Exception) {
            LogUtils.e("公告记录记录查询错误：" + e.localizedMessage)
            return false
        }
    }
}
