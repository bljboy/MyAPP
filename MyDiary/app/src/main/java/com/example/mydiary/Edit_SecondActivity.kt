package com.example.mydiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit__second.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.Key

@Suppress("UNREACHABLE_CODE")
class Edit_SecondActivity : AppCompatActivity() {
    var file: File? = null
    var buffer: ByteArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__second)
        toast("login success")

        file = File(Environment.getExternalStorageDirectory(), "MyDiary.txt")
        Save_BT.setOnClickListener(View.OnClickListener {
            var fos: FileOutputStream? = null
            try {
                var text = Editdiary_ET.text.toString()
                fos = FileOutputStream(file)
                fos.write(text.toByteArray())
                fos.flush()
                if (fos != null) {
                    fos.close()
                    toast("save success")
                }
            } catch (e: Exception) {

            }

        })

        var fis: FileInputStream? = null
        try {

            fis = FileInputStream(file)
            buffer = ByteArray(fis.available())
            fis.read(buffer)
            if (fis != null) {
                fis.close()
                var data = String(buffer!!)
                Editdiary_ET.setText(data)
                Editdiary_ET.setCursorVisible(false)
            }
        } catch (e: Exception) {

        }
        Editdiary_ET.setOnClickListener(View.OnClickListener {
            Editdiary_ET.setCursorVisible(true)
        })
    }

    private var time: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time!! > 2000) {
                toast("click exit again")
                time = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)

    }
}
