package com.example.log2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    val myheper: MySqliteDBHepler = MySqliteDBHepler(this)
    var sp: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
        init()


        eye.setOnClickListener(View.OnClickListener {
            val pos = Et_PassWord.getSelectionStart();
            if (Et_PassWord.getInputType() != (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                //隐藏密码
                eye.setImageDrawable(resources.getDrawable(R.drawable.noeye))
                Et_PassWord.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                //显示密码
                eye.setImageDrawable(resources.getDrawable(R.drawable.eye))
                Et_PassWord.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            Et_PassWord.setSelection(pos);


        })
        //跳转注册界面
        Tv_Register.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.setClass(this, Register().javaClass)
            startActivityForResult(intent, 1)
        })

        //登录按钮单击事件
        Bt_Login.setOnClickListener(View.OnClickListener {
            val user = Et_User.text.toString()
            val password = Et_PassWord.text.toString()
            if (TextUtils.isEmpty(user) or TextUtils.isEmpty(password)) {
                toast("User is null or password is null")
            } else {
                val db = myheper.readableDatabase
                val curson = db.rawQuery(
                    "select * from User where user=? and password=?",
                    arrayOf(user, password)
                )
                if (curson.moveToNext() == true) {
                    toast("Login Success")
//                    val intent1 = Intent()
//                    intent1.setClass(this, LogIn().javaClass)
//                    startActivity(intent1)
                    val intent = Intent()
                    intent.setClass(this, LogIn::class.java)
                    startActivity(intent)
                } else {
                    toast("Check the user and password")
                }
            }
            val CheckBoxLogin: Boolean = Cb_KeepPassword.isChecked
            if (CheckBoxLogin) {
            val editor: SharedPreferences.Editor = sp!!.edit()
            editor.putString("uname", user)
            editor.putString("upswd", password)
            editor.putBoolean("checkboxBoolean", true)
            editor.commit()
        } else {
            val editor: SharedPreferences.Editor = sp!!.edit()
            editor.putString("uname", null)
            editor.putString("upswd", null)
            editor.putBoolean("checkboxBoolean", false)
            editor.commit()
        }

        })
        //自动登录按钮
        Cb_AutoLogin.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val editor = sp!!.edit()
                editor.putBoolean("checkboxBoolean2", true)
                editor.commit()
            } else {
                val editor = sp!!.edit()
                editor.putBoolean("checkboxBoolean2", false)
                editor.commit()
            }
        })
    }

    //记住密码的返回值判断
    fun init() {
        if (sp!!.getBoolean("checkboxBoolean", false)) {
            Et_User.setText(sp!!.getString("uname", null))
            Et_PassWord.setText(sp!!.getString("upswd", null))
            Cb_KeepPassword.setChecked(true)
            Et_User.setCursorVisible(false)
        }
        if (sp!!.getBoolean("checkboxBoolean2", false)) {

            if (TextUtils.isEmpty(Et_User.text.toString())) {
                Cb_AutoLogin.setChecked(false)
            } else {
                Cb_AutoLogin.setChecked(true)
                initLogin()
            }

        }
    }

    //自动登录
    fun initLogin() {
        val db = myheper.readableDatabase
        val s1 = Et_User.text.toString()
        val s2 = Et_PassWord.text.toString()
        val curson = db.rawQuery(
            "select * from User where user=? and password=?",
            arrayOf(s1, s2)
        )
        if (curson.moveToNext() == true) {
            toast("Login Success")
            val intent = Intent()
            intent.setClass(this, LogIn::class.java)
            startActivity(intent)
        } else {
            toast("Check the user and password")
        }
    }

    //接收注册按钮返回的值
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == 1) {
                val s1: String = data!!.getStringExtra("user")
                val s2: String = data!!.getStringExtra("password")
                Et_User.setText(s1)
                Et_PassWord.setText(s2)
                Et_User.setCursorVisible(false)
            }
        }
    }

}
