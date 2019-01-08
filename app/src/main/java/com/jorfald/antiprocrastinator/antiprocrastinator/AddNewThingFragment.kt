package com.jorfald.antiprocrastinator.antiprocrastinator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_new_thing.*
import java.util.*

class AddNewThingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_new_thing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity

        add_save_button.setOnClickListener {
            Thread {
                val thingToDo = ThingToDo(Date().time.toInt(), add_input.text.toString())
                mainActivity.db.userDao().insert(thingToDo)

                mainActivity.runOnUiThread {
                    mainActivity.onBackPressed()
                }
            }.start()
        }
    }
}
