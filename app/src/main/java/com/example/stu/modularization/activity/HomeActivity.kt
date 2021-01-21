package com.example.stu.modularization.activity

import android.graphics.Color
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
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created by stu on 2021/1/19.
 * @function
 * 1.创建所有的fragment
 *
 */

class HomeActivity : BaseActivity(), View.OnClickListener {
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

    }

    private fun initView() {
        home_home.setOnClickListener(this)
        home_message.setOnClickListener(this)
        home_mine.setOnClickListener(this)

        //添加默认显示的fragment
        mHomeFragment = HomeFragment()
        mFragmentManager = supportFragmentManager
        mFragmentTransaction = mFragmentManager?.beginTransaction()
        mFragmentTransaction?.replace(R.id.home_content, mHomeFragment!!)
        mFragmentTransaction?.commit()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.home_home -> {
                fragmentChange(R.id.home_home)
            }
            R.id.home_message -> {
                fragmentChange(R.id.home_message)
            }
            R.id.home_mine -> {
                fragmentChange(R.id.home_mine)
            }
        }
    }

    /**
     * fragment的具体切换方法
     */
    private fun fragmentChange(id: Int?) {
        mFragmentTransaction = supportFragmentManager?.beginTransaction()
        when (id) {
            R.id.home_home -> {
                home_home.setTextColor(Color.BLACK)
                home_message.setTextColor(Color.GRAY)
                home_mine.setTextColor(Color.GRAY)
                //隐藏其他fragment
                hideFragment(mMessageFragment,mFragmentTransaction)
                hideFragment(mMineFragment,mFragmentTransaction)
                //显示homeFragment
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment()
                    mFragmentTransaction?.add(R.id.home_content, mHomeFragment!!)
                } else {
                    mFragmentTransaction?.show(mHomeFragment!!)
                }
            }
            R.id.home_message -> {
                home_home.setTextColor(Color.GRAY)
                home_message.setTextColor(Color.BLACK)
                home_mine.setTextColor(Color.GRAY)
                //隐藏其他fragment
                hideFragment(mHomeFragment,mFragmentTransaction)
                hideFragment(mMineFragment,mFragmentTransaction)
                //显示messageFragment
                if (mMessageFragment == null) {
                    mMessageFragment = MessageFragment()
                    mFragmentTransaction?.add(R.id.home_content, mMessageFragment!!)
                } else {
                    mFragmentTransaction?.show(mMessageFragment!!)
                }
            }
            R.id.home_mine -> {
                home_home.setTextColor(Color.GRAY)
                home_message.setTextColor(Color.GRAY)
                home_mine.setTextColor(Color.BLACK)
                //隐藏其他fragment
                hideFragment(mHomeFragment,mFragmentTransaction)
                hideFragment(mMessageFragment,mFragmentTransaction)
                //显示mineFragment
                if (mMineFragment == null) {
                    mMineFragment = MineFragment()
                    mFragmentTransaction?.add(R.id.home_content, mMineFragment!!)
                } else {
                    mFragmentTransaction?.show(mMineFragment!!)
                }
            }
        }
        mFragmentTransaction?.commit()
    }

    //fragment隐藏代码
    private fun hideFragment(fragment: Fragment?, mFragmentTransaction: FragmentTransaction?) {
        fragment?.let { mFragmentTransaction?.hide(it) }
    }
}