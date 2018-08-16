package com.lyl.smzdk.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lyl.smzdk.R
import com.lyl.smzdk.dao.model.UserInfoModel
import com.lyl.smzdk.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        initView();
    }

    /**
     * 初始化一些参数
     */
    private fun initView() {
        val user = UserInfoModel(mContext).get()

        userinfo_id_txt.text = user.id.toString()
        userinfo_number_txt.text = user.number
        userinfo_nickname_txt.text = user.name
        userinfo_sex_txt.text = if (user.sex == 0) "女" else "男"
        userinfo_createtime_txt.text = user.createTime
    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.userinfo_icon_layout -> { // 头像

            }
            R.id.userinfo_nickname_layout -> { // 昵称

            }
            R.id.userinfo_sex_layout -> { // 性别

            }
            R.id.userinfo_exit_layout -> { // 退出登录
                exitUser()
            }
            else -> {
            }
        }
    }

    /**
     * 退出用户
     */
    private fun exitUser() {
        // 清空用户信息
        UserInfoModel(mContext).clear()
        startActivity(Intent(mContext, LoginActivity::class.java))
        finish()
    }
}
