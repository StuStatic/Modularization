package com.example.stu.modularization.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.stu.modularization.R
import com.example.stu.modularization.fragment.HomeFragment
import com.example.stu.modularization.fragment.MessageFragment
import com.example.stu.modularization.fragment.MineFragment

/**
 * Created by stu on 2021/1/19.
 * @function
 * 1.创建所有的fragment
 *
 */

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private var mFragmentManager: FragmentManager? = null
    private var mFragmentTransaction: FragmentTransaction? = null
    private var mHomeFragment: HomeFragment? = null
    private var mMessageFragment: MessageFragment? = null
    private var mMineFragment: MineFragment? = null
    private var mCurrent: Fragment? = null

    private var mHomeLayout: RelativeLayout? = null
    private var mMessageLayout: RelativeLayout? = null
    private var mMineLayout: RelativeLayout? = null
    private var mPondLayout: RelativeLayout? = null

    private var mHomeView: TextView? = null
    private var mMessageView: TextView? = null
    private var mMineView: TextView? = null
    private var mPondView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        //添加默认显示的fragment
        mHomeFragment = HomeFragment()
        mFragmentManager = supportFragmentManager
        mFragmentTransaction = mFragmentManager?.beginTransaction()
        mFragmentTransaction?.commit()

    }

    private fun initView() {

    }

    override fun onClick(v: View?) {
        when(v?.id){

        }
    }
}