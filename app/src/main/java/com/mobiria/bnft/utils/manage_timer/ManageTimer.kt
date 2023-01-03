package com.mobiria.bnft.utils.manage_timer

import android.os.CountDownTimer
import androidx.databinding.ObservableField

class ManageTimer {
    companion object {

        fun callTimer(sec: Long, mDate: ObservableField<String>) {
            val millis = sec*1000
            object : CountDownTimer(millis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var dd: Long = 0
                    var hh: Long = 0
                    var mm: Long = 0
                    var ss: Long = 0
                    dd = millisUntilFinished.div(86400000)
                    hh = millisUntilFinished.div(3600000)%24
                    mm = (millisUntilFinished.div(60000)%60)
                    ss = (millisUntilFinished.div(1000)%60)
                 /*   t1.text = dd?.toString()+"\nDAYS"
                    t2.text = hh?.toString()+"\nHOURS"
                    t3.text = mm?.toString()+"\nMINs"
                    t4.text = ss?.toString()+"\nSECS"*/

                    mDate.set("$dd D $hh H $mm M $ss S")
                }

                override fun onFinish() {
                }
            }.start()
        }

    }
}