package com.wavywalk.e164standarttringformatter.exceptions

import java.lang.Exception

class NumberIsToSmall(number: String) : Exception() {

    override val message: String

    init {
        message = "${number} is non compliant with E.164, can't be constructed"
    }

}