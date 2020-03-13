package com.example.control

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_Search.setOnClickListener(View.OnClickListener {
            val intent1 = Intent()
            intent1.setClass(this, Search::class.java)
            startActivity(intent1)
        })
        bt_alter.setOnClickListener(View.OnClickListener {
            val intent2 = Intent()
            intent2.setClass(this, Alter::class.java)
            startActivity(intent2)
        })
        bt_add.setOnClickListener(View.OnClickListener {
            val intent3 = Intent()
            intent3.setClass(this, Insert::class.java)
            startActivity(intent3)
        })
        bt_logout.setOnClickListener(View.OnClickListener {
            val intent4 = Intent()
            intent4.setClass(this, Delete::class.java)
            startActivity(intent4)
        })
        start.setOnClickListener(View.OnClickListener {


            try {
                val packageManager = getPackageManager();
                var intent5 = Intent()
                intent5.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent5 = packageManager.getLaunchIntentForPackage("com.example.log2")!!
                startActivity(intent5);
            } catch (e: Exception) {
                toast("No application installed")
            }

            //RunApp("com.example.log2").


        })

    }

    //隐式意图跳转app
    private fun RunApp(packageName: String) {
        var packageName: String? = packageName
        val pi: PackageInfo
        try {
            pi = packageManager.getPackageInfo(packageName, 0)
            val resolveIntent = Intent(Intent.ACTION_MAIN, null)
            resolveIntent.setPackage(pi.packageName)
            val pManager = packageManager
            val apps: List<*> = pManager.queryIntentActivities(
                resolveIntent, 0
            )
            val ri: ResolveInfo? = apps.iterator().next() as ResolveInfo?
            if (ri != null) {
                packageName = ri.activityInfo.packageName
                val className = ri.activityInfo.name
                val intent = Intent(Intent.ACTION_MAIN)
                val cn = ComponentName(packageName, className)
                intent.component = cn
                startActivity(intent)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

}


