package com.yusuftalhaklc.todoapplication

import android.util.Log

class DateEdıtor(var date:String) {

    // 2023-02-22 12:00
    // 0123

    var dateDay :Int
    var dateMonth :Int
    var dateYear :Int

    var dateHour:Int
    var dateMin:Int

    lateinit var monthText :String
    lateinit var dayMonthText :String
    init {
        dateDay = date.substring(8,10).toInt()
        dateYear = date.substring(0,4).toInt()
        dateMonth = date.substring(5,7).toInt()

        dateHour = date.substring(11,13).toInt()
        dateMin = date.substring(14,16).toInt()

        formatEditor()
        dayMonthText = "$monthText - $dateDay  $dateHour:$dateMin"
    }

    private fun formatEditor() {
         monthText = when(dateMonth){
            1 ->{"Jan"}
            2 ->{"Feb"}
            3 ->{"Mar"}
            4 ->{"Apr"}
            5 ->{"May"}
            6 ->{"Jun"}
            7 ->{"Jul"}
            8 ->{"Aug"}
            9 ->{"Sep"}
            10 ->{"Oct"}
            11 ->{"Nov"}
            12 ->{"Dec"}
            else -> {""}
        }
    }

}