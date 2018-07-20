package com.lyl.smzdk.network.entity.moments

/**
 * Author: lyl
 * Date Created : 2018/7/20.
 */

data class MomentListBean(
        var moments: MomentBean,
        var type: String,
        var message: String
)

data class MomentBean(
        var id: Long = 0,
        var user: UserBean,
        var content: String,
        var images: ImageBean,
        var display_type: String,
        var create_time: Long,
        var like_count: String,
        var comment_count: String,
        var share_count: String,
        var go_detail_count: String,

        var category_id: String,
        var category_type: String,
        var category_name: String,
        var isCategory_visible: Boolean,

        var share_type: String,
        var type: String,

        var has_hot_comments: String
)

data class UserBean(
        var user_id: Long,
        var user_name: String,
        var user_icon: String,
        var introduction: String,// 签名
        var isUser_verified: Boolean,// 认证用户
        var isUser_vip: Boolean,// vip 用户
        var like_count: String,// 点赞数
        var followers: String,// 粉丝数
        var isfollowing: Boolean // 是否关注
)

data class ImageBean(var width: String, var height: String, var url: String)


data class CommentsBean(
        var id: Long = 0,
        var group_id: Long = 0,

        var user_id: Long = 0,
        var user_name: String,
        var user_icon: String,
        var isUser_verified: Boolean = false,

        var text: String,
        var create_time: String,

        var digg_count: String,
        var is_digg: String,

        var platform_id: String,
        var platform: String
)