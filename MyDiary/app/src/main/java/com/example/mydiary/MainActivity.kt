package com.example.mydiary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {


    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var shared = getSharedPreferences("Info", Context.MODE_PRIVATE);
        var editor = shared.edit()
        editor.putString("account", "123")
        editor.putString("password", "123")
        editor.commit()
        loading_PB.setVisibility(View.GONE)


        //登录按钮的监听事件
        Login_BT.setOnClickListener(View.OnClickListener {

            var accounttext = account_ET.text.toString()
            var passwordtext = password_ET.text.toString()
            //获取SharedPreferences中的数据
            val account = shared.getString("account", null)
            val password = shared.getString("password", null)
            //判断输入框的账号和密码是否正确
            if (accounttext.equals(account) and passwordtext.equals(password)) {
                loading_PB.setVisibility(View.VISIBLE)
                Thread {
                    Thread.sleep(2000)
                    var intent = Intent()
                    intent.setClass(this, Edit_SecondActivity::class.java)
                    startActivity(intent)
                    finish()

                }.start()


            } else {
                toast("Check account or password")
            }

            //写入CheckBox控件的状态默认1，默认是未勾选是true，反之勾选了为false
            if (Remerberpassword_CB.isChecked == true) {
                var editor = shared.edit()
                editor.putBoolean("checkbox", true)
                editor.commit()
            } else {
                var editor = shared.edit()
                editor.putBoolean("checkbox", false)
                editor.commit()
            }
        })


        //当复选框为勾选状态时，即是记住密码
        if (shared.getBoolean("checkbox", false)) {
            account_ET.setText(shared.getString("account", null))
            password_ET.setText(shared.getString("account", null))
            Remerberpassword_CB.setChecked(true)
            account_ET.setCursorVisible(false)//不显示光标
        }
        account_ET.setOnClickListener(View.OnClickListener {
            account_ET.setCursorVisible(true)
        })

    }


}
