package com.example.control

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alter.*
import org.jetbrains.anko.toast


class Alter : AppCompatActivity() {
    var resolver: ContentResolver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter)
        //et_alter.setVisibility(View.GONE) //隐藏控件


        bt_update.setOnClickListener(View.OnClickListener {
            resolver = contentResolver
            var user = alter_user.text.toString()
            var password = alter_password.text.toString()
            try {
                val cursor1 = resolver!!.query(
                    Uri.parse("content://com.example.log2.Provider"),
                    arrayOf("user", "password"),
                    "user=? and password=?",
                    arrayOf(user, password),
                    null
                )
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password)) {
                    toast("Account number or password is empty")

                } else {

                    if (cursor1!!.moveToNext()) {
                        view_password.setImageDrawable(resources.getDrawable(R.drawable.smaile2))
                        var newpassword = alter_newpassword.text.toString()
                        var newpassword2 = alter_newpassword2.text.toString()
                        if (TextUtils.isEmpty(newpassword) || TextUtils.isEmpty(newpassword2)) {
                            toast("The password is empty")
                        } else {
                            if (newpassword2 == newpassword) {
                                val values = ContentValues()
                                values.put("password", newpassword)
                                resolver = contentResolver
                                resolver!!.update(
                                    Uri.parse("content://com.example.log2.Provider"),
                                    values,
                                    "user=?",
                                    arrayOf(user)
                                )
                                toast("Password successfully changed")
                                alter_newpassword.setText("")
                                alter_newpassword2.setText("")
                            } else {
                                toast("Entered passwords differ")
                            }
                        }
                    } else {
                        toast(" the account with this keycode was not found")
                        view_password.setImageDrawable(resources.getDrawable(R.drawable.nosmaile))
                    }
                    cursor1!!.close()
                }
            } catch (e: Exception) {
                toast("Please start provider app")
            }
        })

    }

}
