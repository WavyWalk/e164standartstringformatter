package com.wavywalk.e164standarttringformatter.exceptions

import com.wavywalk.e164standarttringformatter.numberingplandata.CountryData
import java.lang.Exception

class ResultPhoneNumberExceedsAllowedLength(val country: CountryData, val number: String) : Exception() {

    override val message: String

    init {
        message = "${number} is non compliant with E.164, §6.2.1 does not allow such length, exceeds 15 digits"
    }


}
