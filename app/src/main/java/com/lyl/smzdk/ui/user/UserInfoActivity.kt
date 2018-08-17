package com.lyl.smzdk.ui.user

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.lyl.smzdk.R
import com.lyl.smzdk.dao.model.UserInfoModel
import com.lyl.smzdk.network.Network
import com.lyl.smzdk.network.entity.myapi.User
import com.lyl.smzdk.network.imp.MyApiImp
import com.lyl.smzdk.ui.BaseActivity
import com.lyl.smzdk.utils.DialogUtils
import com.lyl.smzdk.utils.ImgUtils
import kotlinx.android.synthetic.main.activity_user_info.*


class UserInfoActivity : BaseActivity(), View.OnClickListener {

    //调用系统相册-选择图片
    private val IMAGE = 11001

    private lateinit var mName: String
    private lateinit var mIcon: String
    private var mSex: Int = 0

    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        initView();
        initData()
    }

    /**
     * 初始化一些参数
     */
    private fun initData() {
        mUser = UserInfoModel(mContext).get()

        mIcon = mUser.icon
        if (TextUtils.isEmpty(mUser.icon)) {
            userinfo_icon_img.setActualImageResource(if (mUser.sex == 0) R.drawable.ic_sex_girl_default else R.drawable.ic_sex_boy_default)
        } else {
            ImgUtils.load(mContext, mUser.icon, userinfo_icon_img)
        }
        userinfo_id_txt.text = mUser.id.toString()
        userinfo_number_txt.text = mUser.number
        mName = mUser.name
        userinfo_nickname_txt.text = mUser.name
        mSex = mUser.sex
        userinfo_sex_txt.text = if (mUser.sex == 0) getString(R.string.girl) else getString(R.string.boy)
        userinfo_createtime_txt.text = mUser.createTime.subSequence(0, 10)
    }

    /**
     * 初始化布局
     */
    private fun initView() {
        userinfo_actionbar.setModelBack(R.string.user_title, mActivity)

        userinfo_icon_layout.setOnClickListener(this)
        userinfo_nickname_layout.setOnClickListener(this)
        userinfo_sex_layout.setOnClickListener(this)
        userinfo_exit_layout.setOnClickListener(this)
    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.userinfo_icon_layout -> { // 头像
                //调用相册
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, IMAGE)
            }

            R.id.userinfo_nickname_txt, R.id.userinfo_nickname_layout -> { // 昵称
                changeNickNameDialog()
            }

            R.id.userinfo_sex_txt, R.id.userinfo_sex_layout -> { // 性别
                changeSexDialog()
            }

            R.id.userinfo_exit_layout -> { // 退出登录
                exitUser()
            }

            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            val filePathColumns = arrayOf(MediaStore.Images.Media.DATA)
            val c = contentResolver.query(selectedImage!!, filePathColumns, null, null, null)
            c!!.moveToFirst()
            val columnIndex = c.getColumnIndex(filePathColumns[0])
            // 图片地址
            val imagePath = c.getString(columnIndex)
            c.close()
            ImgUtils.load(mContext, "file://$imagePath", userinfo_icon_img)
        }
    }

    /**
     * 修改昵称 的对话框
     */
    private fun changeNickNameDialog() {
        val edt = EditText(mContext);
        edt.maxLines = 1
        edt.filters = arrayOf(InputFilter.LengthFilter(24))
        edt.setText(mName)

        AlertDialog.Builder(mContext)//
                .setTitle(getString(R.string.change_name))
                .setView(edt)//
                .setPositiveButton(R.string.ok) { dialog, which ->

                    mName = edt.text.toString().trim()
                    updateUserInfo()
                    userinfo_nickname_txt.text = mName
                    dialog.dismiss()

                }//
                .setNegativeButton(R.string.cancel, null)//
                .create()//
                .show()
    }

    /**
     * 修改性别 的对话框
     */
    private fun changeSexDialog() {
        AlertDialog.Builder(mContext)//
                .setSingleChoiceItems(R.array.sex_array, mSex) { dialog, which ->

                    mSex = which
                    updateUserInfo()
                    userinfo_sex_txt.text = if (which == 0) getString(R.string.girl) else getString(R.string.boy)
                    dialog.dismiss()

                }//
                .create()//
                .show()
    }

    /**
     * 向服务器更新用户信息
     */
    private fun updateUserInfo() {
        val parameter = mapOf<String, String>(Pair("name", mName), Pair("icon", mIcon))

        val updateUser = Network.getMyApi().updateUser(mUser.id, mSex, parameter)
        MyApiImp<User>().request(updateUser, object : MyApiImp.NetWorkCallBack<User> {

            override fun onSuccess(obj: User) {
                val userModel = UserInfoModel(mContext)
                userModel.save(obj)

                // 保存成功
                t(getString(R.string.save_success))
            }

            override fun onFail(code: Int, msg: String?) {
                DialogUtils.showErrorDialog(mContext, msg)
            }
        })
    }

    /**
     * 退出用户
     */
    private fun exitUser() {
        AlertDialog.Builder(mContext)//
                .setTitle(getString(R.string.dialog_exit_title))//
                .setMessage(getString(R.string.dialog_exit_msg))//
                .setPositiveButton(R.string.ok) { dialog, which ->

                    dialog.dismiss()
                    // 清空用户信息
                    UserInfoModel(mContext).clear()
                    startActivity(Intent(mContext, LoginActivity::class.java))
                    finish()

                }//
                .setNegativeButton(R.string.cancel, null)//
                .create()//
                .show()


    }
}
