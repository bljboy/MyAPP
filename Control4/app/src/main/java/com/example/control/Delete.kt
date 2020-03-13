package com.example.control

import android.content.ContentResolver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_alter.*
import kotlinx.android.synthetic.main.activity_delete.*
import org.jetbrains.anko.toast

class Delete : AppCompatActivity() {
    var resolver: ContentResolver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        btdelete.setOnClickListener(View.OnClickListener {
            resolver = contentResolver
            var deleteuser = delete_user.text.toString()
            var deletepassword = delete_password.text.toString()
            try {
                if (TextUtils.isEmpty(deleteuser) || TextUtils.isEmpty(deletepassword)) {
                    toast("Account number or password is empty")
                } else {
                    val cursor1 = resolver!!.query(
                        Uri.parse("content://com.example.log2.Provider"),
                        arrayOf("user", "password"),
                        "user=? and password=?",
                        arrayOf(deleteuser, deletepassword),
                        null
                    )

                    if (cursor1!!.moveToNext()) {
                        val delete = resolver!!.delete(
                            Uri.parse("content://com.example.log2.Provider"),
                            "user=?", arrayOf(deleteuser)
                        )
                        if (delete != null) {
                            toast("logout success")
                            finish()
                        }

                    } else {
                        toast("the account with this keycode was not found")
                    }
                }
            } catch (e: Exception) {
                toast("Please start provider app")
            }


        })
    }
}
