package com.lyl.smzdk.network.entity.myapi

import java.util.*

/**
 * Author: lyl
 * Date Created : 2018/8/3.
 */

/**
 * API 返回数据的基类
 */
data class BaseCallBack<T>(
        var code: Int = 0,
        var msg: String,
        var data: T)


/**
 * 用户信息
 */
data class User(
        var id: Long = 0L,
        var number: String,
        var name: String,
        var icon: String,
        var signature: String,
        var sex: Int = 0,// 性别
        var birth: String,
        var phone: String,
        var email: String,
        var province: String,
        var city: String,
        var vipGrade: Int = 0,
        var vipLimitDate: Date,// 会员过期时间,
        var integral: Long = 0L,
        var closeDays: Int = 0,
        var createTime: Date,
        var updateTime: Date
)

/**
 * 会员充值
 */
data class VipRecharge(
        var id: Long,
        var user_id: Long,
        var money: Double,
        private var vipGrade: Int = 1,
        private var duration: Int = 1,
        private var fromRecharge: Int = 0,
        var createTime: Date
)

/**
 * bt 搜索
 */
data class BtSearch(
        var id: Long,
        var userId: Long,
        var uuid: String,
        var content: String,
        var createTime: Date
)

/**
 * 公告
 */
data class Announcement(
        var id: Long,
        var userId: Long,
        var title: String,
        var content: String,
        var authorName: String,
        var createTime: Date
)

/**
 * 建议反馈
 */
data class Feedback(
        var id: Long,
        var userId: Long,
        var title: String,
        var content: String,
        var userName: String,
        var adminReply: String,
        var createTime: Date
)