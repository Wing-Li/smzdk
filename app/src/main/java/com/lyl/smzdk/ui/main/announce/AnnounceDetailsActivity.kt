package com.lyl.smzdk.ui.main.announce

import android.os.Bundle
import com.lyl.smzdk.R
import com.lyl.smzdk.dao.db.imp.AnnounceImp
import com.lyl.smzdk.network.Network
import com.lyl.smzdk.network.entity.myapi.Announcement
import com.lyl.smzdk.network.imp.MyApiImp
import com.lyl.smzdk.ui.BaseActivity
import com.lyl.smzdk.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_announce_details.*

/**
 * Author: lyl
 * Date Created : 2018/8/14.
 */
class AnnounceDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announce_details)

        actionbar.setModelBack(R.string.announce, mActivity)

        initData();
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        showDialog()

        val lastAnnouncement = Network.getMyApi().lastAnnouncement
        MyApiImp<Announcement>().request(lastAnnouncement, object : MyApiImp.NetWorkCallBack<Announcement> {

            override fun onSuccess(entiry: Announcement) {
                hideDialog()

                // 设置布局
                initView(entiry)

                // 如果公告没有读过，将公告存进数据库
                val announceImp = AnnounceImp(mContext)
                if (!announceImp.isExits(entiry.id)) {
                    announceImp.save(entiry.id, entiry.title)
                }
            }

            override fun onFail(code: Int, msg: String?) {
                hideDialog()
                DialogUtils.showErrorDialog(mContext, msg)
            }
        })
    }

    /**
     * 设置布局
     */
    private fun initView(info: Announcement) {
        announce_title.text = info.title
        announce_author.text = info.authorName
        announce_date.text = info.createTime.subSequence(0, 11)
        announce_content.text = info.content
    }
}
