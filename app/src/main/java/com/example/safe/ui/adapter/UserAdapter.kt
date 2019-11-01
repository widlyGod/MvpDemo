package com.example.safe.ui.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.safe.R
import com.example.safe.db.entity.UserBean
import java.text.SimpleDateFormat
import java.util.*

class UserAdapter (list: List<UserBean>): BaseQuickAdapter<UserBean, BaseViewHolder>(R.layout.item_user, list) {

    @SuppressLint("SimpleDateFormat")
    override fun convert(helper: BaseViewHolder, item: UserBean) {
        helper.setText(R.id.tv_user_name,item.name)
        helper.setText(R.id.tv_user_password,item.password)
        val formatter = SimpleDateFormat("yyyy.MM.dd")
        val date = Date(item.createTime)
        helper.setText(R.id.tv_user_time,formatter.format(date))
    }

}