package com.lyl.smzdk.ui.user

import android.os.Bundle
import com.lyl.smzdk.R
import com.lyl.smzdk.ui.BaseActivity
import com.lyl.smzdk.utils.PayUtils
import kotlinx.android.synthetic.main.activity_recharge_vip.*

class RechargeVipActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_vip)

        vip_actionbar.setModelBack(R.string.recharge_vip, mActivity)


        vip_weixin_pay.setOnClickListener {
            PayUtils.payWeixin(mContext, mUserInfoModel.number)
        }

        vip_alipay_pay.setOnClickListener {
            PayUtils.payAlipay(mContext, mUserInfoModel.number)
        }
    }

}
