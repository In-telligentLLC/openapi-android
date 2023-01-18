package com.intelligent.openapidemo.utils

import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {

    companion object{

        fun changeDateFormat(date:Long): String {

            return SimpleDateFormat("MM/dd/yyyy").format(Date(date))

        }


    }
}