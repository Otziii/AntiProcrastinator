package com.jorfald.antiprocrastinator.antiprocrastinator

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_things_details.view.*


@SuppressLint("ValidFragment")
class ThingsDetailsFragment constructor(private val item: ThingToDo) : Fragment() {

    private lateinit var selectWrapper: LinearLayout
    private lateinit var timerWrapper: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_things_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.setRandomButton(null)
        mainActivity.setDeleteButton(View.OnClickListener {
            Thread {
                mainActivity.db.userDao().delete(item)
                mainActivity.runOnUiThread {
                    mainActivity.onBackPressed()
                }
            }.start()
        })

        selectWrapper = view.time_select_wrapper
        selectWrapper.visibility = VISIBLE

        timerWrapper = view.timer_wrapper
        timerWrapper.visibility = GONE

        view.title_text.text = item.toDoName

        if (item.minutes > 0) {
            view.minutes_input.setText(item.minutes.toString())
        }

        view.time_set_button.setOnClickListener {
            val text = view.minutes_input.text.toString()
            if (text.toIntOrNull() != null) {
                startTimer(text.toInt())

                if (context != null) {
                    val imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            } else {
                Toast.makeText(context, "Du må skrive inn et heltall", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startTimer(minutes: Int) {
        selectWrapper.visibility = GONE
        timerWrapper.visibility = VISIBLE


        val seconds = minutes * 60 * 1000
        object : CountDownTimer(seconds.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val minutesLeft = secondsLeft / 60
                val secondsRemaining = secondsLeft % 60

                var timeString: String
                timeString = if (minutesLeft < 10) {
                    "0$minutesLeft"
                } else {
                    "$minutesLeft"
                }
                timeString += ":"
                timeString += if (secondsRemaining < 10) {
                    "0$secondsRemaining"
                } else {
                    "$secondsRemaining"
                }

                timerWrapper.timer_numbers.text = timeString
            }

            override fun onFinish() {
                Toast.makeText(context, "Ferdig!", Toast.LENGTH_LONG).show()
                selectWrapper.visibility = VISIBLE
                timerWrapper.visibility = GONE
            }
        }.start()
    }
}
