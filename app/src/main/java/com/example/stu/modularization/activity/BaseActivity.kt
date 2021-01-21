package com.example.stu.modularization.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

/**
 * Created by stu on 2021/1/19.
 * @function 为所有的activity提供公共的行为
 */

abstract class BaseActivity : AppCompatActivity() {

    public var TAG: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getCustomLayoutId())
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        TAG = componentName.shortClassName
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    open fun getCustomLayoutId(): Int {
        return getLayoutId()
    }

    fun Context.getLayoutId(pageName: String = javaClass.simpleName): Int {
        var type = "Activity"
        if (pageName.endsWith("Fragment")) {
            type = "Fragment"
        }
        val name = pageName.substring(0, pageName.indexOf(type))
        val chars = name.toCharArray()
        val buf = StringBuffer()
        buf.append(type.toLowerCase())
        for (c: Char in chars) {
            if (c in 'A'..'Z') {
                buf.append("_" + c.toLowerCase())
            } else {
                buf.append(c)
            }
        }
        return resources.getIdentifier(buf.toString(), "layout", packageName)
    }
}