package com.mobiria.bnft.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateFormatChanger {
    companion object {
        fun convertTimeAccordingTimeZone(dtStart: String?): String? {
            var date: Date? = null
            var dateTime = ""
            val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
            format.timeZone = TimeZone.getTimeZone("UTC")
            try {
                date = format.parse(dtStart)
                System.out.println(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy hh:mm aa")
            //  val dateFormat = SimpleDateFormat("dd-MMM-yyyy, EEE hh:mm a")
            dateTime = dateFormat.format(date)
            println("Current Date Time : $dateTime")
            return dateTime
        }

        fun convertDateTime(dtStart: String?): String? {
            var date: Date? = null
            var dateTime = ""
            val format = SimpleDateFormat("yyyy-MM-dd")
            format.timeZone = TimeZone.getTimeZone("UTC")
            try {
                date = format.parse(dtStart)
                System.out.println(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
           // val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy hh:mm aa")
              val dateFormat = SimpleDateFormat("EEEE, dd-MMM-yyyy ")
            dateTime = dateFormat.format(date)
            println("Current Date Time : $dateTime")
            return dateTime
        }

    }
}