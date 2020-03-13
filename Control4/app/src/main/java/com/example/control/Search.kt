package com.example.control

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.bt_Search
import org.jetbrains.anko.toast
import java.util.*


class Search : AppCompatActivity() {
    var resolver: ContentResolver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var list: ArrayList<Food> = ArrayList()
        Et_Search.setFocusable(true)
        Et_Search.setFocusableInTouchMode(true)
        Et_Search.requestFocus()
        val timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    val inputManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.showSoftInput(Et_Search, 0)
                }
            },
            200
        )


        bt_Search.setOnClickListener(View.OnClickListener {
            var sear: String = Et_Search.text.toString()
            list.removeAll(list)
            resolver = contentResolver
            val cursor = resolver!!.query(
                Uri.parse("content://com.example.log2.Provider"),
                arrayOf("user"), "user like ?", arrayOf("%${sear}%"), null
            )
            if (TextUtils.isEmpty(sear)) {
                toast("account code entry")
            } else {
                try {

                    while (cursor!!.moveToNext()) {
                        val user = cursor.getString(0)
                        list?.add(Food(user))
                    }
                    lv_list.adapter = MyAdapter(this, list)
                    cursor!!.close()
                } catch (e: Exception) {
                    toast("Please start provider app")
                }
            }


        })

    }

    class MyAdapter(var context: Context, var list: ArrayList<Food>) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var vH: ViewHolder? = null
            var view: View? = null
            if (convertView == null) {
                vH = ViewHolder()
                view = View.inflate(context, R.layout.row, null);
                vH.textView = view.findViewById(R.id.tv1)
                view.tag = vH
            } else {
                view = convertView
                vH = view.tag as ViewHolder
            }
            vH.textView?.text = list.get(position).user
            return view!!
        }

        inner class ViewHolder {
            var textView: TextView? = null
        }

        override fun getItem(position: Int): Any {
            return list.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }
    }

    class Food {

        var user: String? = null

        constructor(user: String?) {
            this.user = user
        }
    }


}
