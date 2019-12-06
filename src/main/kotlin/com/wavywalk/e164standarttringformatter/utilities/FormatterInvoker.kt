package com.wavywalk.e164standarttringformatter.utilities

import com.wavywalk.e164standarttringformatter.Application
import com.wavywalk.e164standarttringformatter.ToE164AllowingExitCodePrefixFormatter
import com.wavywalk.e164standarttringformatter.ToE164ForSpecificCountryFormatter

object FormatterInvoker {
    fun formatGerman(numbers: MutableList<String>) {
        numbers.forEach { number ->
            val formatter = ToE164ForSpecificCountryFormatter(number, Application.germanyData)
            try {
                println("---->  " + formatter.format())
            } catch (exception: Exception) {
                println(exception.message)
            }
            formatter.resultNumberingPlanEntry?.also {
                println(it.nationalDestinationCodeType)
                println(it.nationalDestinationCodeTypeSpecifier)
            }
            println("------------------------------------")
        }
    }

    fun formatInternational(numbers: MutableList<String>) {
        numbers.forEach { number ->
            val formatter = ToE164AllowingExitCodePrefixFormatter(number)
            val result = formatter.extract()
            if (result.keys.size == 0 && formatter.recordedErrors.isNotEmpty()) {
                formatter.recordedErrors.forEach {
                    println(it.message)
                }
            } else {
                if (result.keys.size == 0) {
                    println("${number} can not be converted to E164 due to no country has such identifiers")
                } else {
                    result.forEach {
                        println("${it.key} ---> ${it.value}")
                    }
                }
                println("------------------------------------")
            }
        }
    }
}