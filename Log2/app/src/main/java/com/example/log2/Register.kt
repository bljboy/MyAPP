package com.example.log2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class Register : AppCompatActivity() {
    val myheper: MySqliteDBHepler = MySqliteDBHepler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val contentValues = ContentValues()
        Bt_Register.setOnClickListener(View.OnClickListener {

            var registeruser = Et_RegisterUser.text.toString()
            var registerpassword = Et_RegisterPassWord.text.toString()
            var registerpassword_again = Et_RegisterPassWord_Again.text.toString()
            if (TextUtils.isEmpty(registeruser) or TextUtils.isEmpty(registerpassword) or TextUtils.isEmpty(
                    registerpassword_again
                )
            ) {
                toast("User is null or password is null")
            } else {
                val db = myheper.readableDatabase
                val c =
                    db.rawQuery("select * from User where user=?", arrayOf(registeruser))
                if (c.moveToNext() == true) {
                    toast("You input the username already exists")
                } else {
                    toast("0000")
                    if ((registerpassword_again == registerpassword)
                    ) {
                        contentValues.put("user", registeruser)
                        contentValues.put("password", registerpassword)
                        val result = db.insert("User", null, contentValues)
                        if (result != null) {

                            toast("Register succee")
                            val rIntent = Intent();
                            rIntent.putExtra("user", registeruser)
                            rIntent.putExtra("password", registerpassword)
                            setResult(1, rIntent);
                            finish();

                            /*var intent = Intent()
                            var bundle = Bundle()
                            bundle.putString("user", registeruser)
                            bundle.putString("password", registerpassword)
                            intent.putExtras(bundle)
                            setResult(1, intent)
                            finish()*/
                        } else {
                            toast("Register fail")
                        }
                    } else {
                        toast("Entered passwords differ")
                    }
                }
            }
        })
    }
}
