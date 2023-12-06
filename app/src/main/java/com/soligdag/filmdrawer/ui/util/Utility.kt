package com.soligdag.filmdrawer.ui.util

object Utility {
    fun GetDisplayTimeFromMinutes(minutes : Int) : String {
        return if(minutes<60) {
            minutes.toString()+"h"
        } else {
            val hours : Int = minutes/60
            val minutes : Int = minutes%60
            hours.toString() + "h " + minutes.toString() + "m"
        }
    }
}