package com.example.control

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_insert.*
import org.jetbrains.anko.toast
import java.lang.Exception

class Insert : AppCompatActivity() {
    var resolver: ContentResolver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        val contentValues = ContentValues()
        bt_addUser.setOnClickListener(View.OnClickListener {
            var adduser = Et_addUser.text.toString()
            var addpassword = Et_addPassword.text.toString()
            var addpasswordAgain = Et_addPassword_Again.text.toString()
            try {
                if (TextUtils.isEmpty(adduser) or TextUtils.isEmpty(addpassword) or TextUtils.isEmpty(
                        addpasswordAgain
                    )
                ) {
                    toast("User is null or password is null")
                } else {
                    resolver = contentResolver
                    val cursor1 = resolver!!.query(
                        Uri.parse("content://com.example.log2.Provider"),
                        arrayOf("user"),
                        "user=?",
                        arrayOf(adduser),
                        null
                    )
                    if (cursor1!!.moveToNext()) {
                        view_adduser.setImageDrawable(resources.getDrawable(R.drawable.no))
                        toast("Account already exists")
                    } else {
                        if ((addpasswordAgain == addpassword)
                        ) {
                            contentValues.put("user", adduser)
                            contentValues.put("password", addpassword)

                            val reulest = resolver!!.insert(
                                Uri.parse("content://com.example.log2.Provider"), contentValues
                            )
                            if (reulest == null) {
                                view_adduser.setImageDrawable(null)
                                toast("Register succee")
                                finish()
                            } else {
                                toast("Register fail")
                            }
                        } else {
                            toast("Entered passwords differ")
                        }

                        cursor1!!.close()
                    }
                }
            } catch (e: Exception) {
                toast("Please start provider app")
            }


        })
    }
}
