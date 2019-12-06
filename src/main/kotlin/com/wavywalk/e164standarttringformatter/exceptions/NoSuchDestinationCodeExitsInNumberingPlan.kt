package com.wavywalk.e164standarttringformatter.exceptions

import com.wavywalk.e164standarttringformatter.numberingplandata.CountryData
import java.lang.Exception

class NoSuchDestinationCodeExitsInNumberingPlan(val country: CountryData, number: String): Exception() {

    override val message: String

    init {
        message = "${number} is not valid number according to ${country.name} numbering plan"
    }

}