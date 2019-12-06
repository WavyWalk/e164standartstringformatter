package com.wavywalk.e164standarttringformatter.utilities

object DigitCharsPlucker {

    fun pluckIntoList(input: String, targetArray: MutableList<Int>) {
        input.forEach {
            if (it.isDigit()) {
                targetArray.add(it - '0')
            }
        }
    }

}