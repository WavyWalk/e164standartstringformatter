package com.wavywalk.e164standarttringformatter.utilities

import java.io.BufferedReader
import java.io.InputStreamReader

typealias HandlerType = (bufferedReader: BufferedReader)->Unit

object ApplicationUserInputListener {

    val internatinalFormatterHandler: HandlerType =  { bufferedReader: BufferedReader ->
        println("enter your number or numbers delimeted by comma or enter (menu)")
        val input = bufferedReader.readLine()
        when(input) {
            "menu" -> {
                currentListenerHandler = menuHandler
            }
            "x" -> {
                currentListenerHandler = null
            }
            else -> {
                val numbers = input.split(",").map {
                    it.trim()
                }
                FormatterInvoker.formatInternational(numbers.toMutableList())
            }
        }

    }

    val germanFormatterHandler: HandlerType = { bufferedReader ->
        println("enter your number or numbers delimeted by comma")
        val input = bufferedReader.readLine()

        when(input) {
            "menu" -> {
                currentListenerHandler = menuHandler
            }
            "x" -> {
                currentListenerHandler = null
            }
            else -> {
                val numbers = input.split(",").map {
                    it.trim()
                }
                FormatterInvoker.formatGerman(numbers.toMutableList())
            }
        }

    }

    var currentListenerHandler: HandlerType?  = { bufferedReader: BufferedReader ->
        currentListenerHandler = menuHandler
    }

    val menuHandler = {bufferedReader: BufferedReader ->
        println("""
            Which converter do you like to try:
            International (i) or German (g). enter (x) anytime to close?
        """.trimIndent())
        val input = bufferedReader.readLine()
        if (input == "i") {
            currentListenerHandler = internatinalFormatterHandler
        } else if (input == "g") {
            currentListenerHandler = germanFormatterHandler
        } else {
            println("invalid input")
        }
    }

    fun run() {
        val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
        bufferedReader.use {
            while (currentListenerHandler != null) {
                currentListenerHandler!!.invoke(it)
            }
        }
        println("bye")
    }

}