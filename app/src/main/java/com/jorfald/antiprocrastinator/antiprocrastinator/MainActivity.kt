package com.jorfald.antiprocrastinator.antiprocrastinator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.title = "Anti-prokrastinator"
        this.db = AppDatabase.getInstance(this)!!

        NotificationHelper().setUpNotifications()

        showFragment(ThingsListFragment())
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count <= 1) {
            finishAffinity()
        } else {
            supportFragmentManager.popBackStack()
        }

    }

    fun showFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(fragment.tag)
        transaction.commit()
    }

    fun setRandomButton(clickListenerOrNullToHide: View.OnClickListener?) {
        if (clickListenerOrNullToHide != null) {
            random_button_toolbar.visibility = VISIBLE
            random_button_toolbar.setOnClickListener(clickListenerOrNullToHide)
        } else {
            random_button_toolbar.visibility = GONE
        }
    }

    fun setDeleteButton(clickListenerOrNullToHide: View.OnClickListener?) {
        if (clickListenerOrNullToHide != null) {
            delete_button_toolbar.visibility = VISIBLE
            delete_button_toolbar.setOnClickListener(clickListenerOrNullToHide)
        } else {
            delete_button_toolbar.visibility = GONE
        }
    }


}
