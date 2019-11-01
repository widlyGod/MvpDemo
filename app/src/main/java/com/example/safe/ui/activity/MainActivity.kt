package com.example.safe.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.example.safe.R
import com.example.safe.base.BaseActivity
import com.example.safe.component.DaggerMainComponent
import com.example.safe.contract.MainContract
import com.example.safe.db.entity.UserBean
import com.example.safe.moudule.MainModule
import com.example.safe.presenter.MainPresenter
import com.example.safe.ui.adapter.UserAdapter
import com.example.safe.utils.*
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.activity_main.*
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<MainPresenter>(), MainContract.View {

    private val mUserList = mutableListOf<UserBean>()
    private val mUserAdapter by lazy { UserAdapter(mUserList) }
    private lateinit var materialDialog: MaterialDialog
    private lateinit var publicKey: PublicKey
    private lateinit var privateKey: PrivateKey
    private val desPassword = "12345678"//密钥长度，des长度8位

    override fun login(encode: String, boolean: Boolean) {
        MaterialDialog(this).show {
            title(if (boolean) R.string.login_success else R.string.login_fail)
            message(null, "加密后：${encode}")
            debugMode(false)
            lifecycleOwner(this@MainActivity)
        }
    }


    override fun queryAll(list: List<UserBean>) {
        materialDialog = MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            customView(R.layout.custom_view, scrollable = true, horizontalPadding = true)
            val recycler: RecyclerView = this.getCustomView()
                .findViewById(R.id.recycler)
            recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mUserAdapter.apply {
                    setNewData(list)
                }
            }
            title(R.string.user)
            positiveButton(R.string.clean) {
                mPresenter?.cleanUser()
            }
            negativeButton(android.R.string.cancel)
            debugMode(false)
            lifecycleOwner(this@MainActivity)
        }
    }

    override fun register() {
        MaterialDialog(this).show {
            message(R.string.register)
            debugMode(false)
            lifecycleOwner(this@MainActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainComponent.builder().mainModule(MainModule(this, this)).build().inject(this)
        init()
        initKey()
    }

    private fun initKey() {
        val generator = KeyPairGenerator.getInstance("RSA")
        val keyPair = generator.genKeyPair()//生成密钥对
        publicKey = keyPair.public//公钥
        privateKey = keyPair.private//私钥
    }

    @SuppressLint("CheckResult")
    private fun init() {
        bt_register.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            if (et_name.text!!.trim().isNotBlank() && et_password.text!!.trim().isNotBlank()) {
                mPresenter?.saveUser(
                    et_name.text!!.trim().toString(),
                    encodeSha1(et_password.text!!.trim().toString())
                )
            }
        }.bindToLifecycle(this)
        bt_login.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            if (et_name.text!!.trim().isNotBlank() && et_password.text!!.trim().isNotBlank()) {
                var name = et_name.text!!.trim().toString()
                var password = et_password.text!!.trim().toString()
                MaterialDialog(this).show {
                    title(R.string.choose_encode_type)
                    listItemsSingleChoice(R.array.encodeType, initialSelection = 0) { _, index, _ ->
                        var rsa = ""
                        var des = ""
                        mPresenter?.query(
                            name,
                            when (index) {
                                0 -> encodeMd5(password)
                                1 -> encodeSha1(password)
                                2 -> {
                                    des = encrypt(encodeSha1(password), desPassword)
                                    des
                                }
                                3 -> {
                                    rsa = encryptByPublicKey(
                                        encodeSha1(password),
                                        publicKey
                                    )
                                    rsa
                                }
                                else -> encodeSha1(password)
                            },
                            when (index) {
                                0 -> encodeMd5(password)
                                1 -> encodeSha1(password)
                                2 -> decrypt(des, desPassword)
                                3 -> decryptByPrivateKey(rsa, privateKey)
                                else -> encodeSha1(password)
                            }
                        )
                    }
                    positiveButton(R.string.choose)
                    debugMode(false)
                    lifecycleOwner(this@MainActivity)
                }

            }
        }.bindToLifecycle(this)
        tv_watch.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            mPresenter?.load()
        }.bindToLifecycle(this)
    }
}
