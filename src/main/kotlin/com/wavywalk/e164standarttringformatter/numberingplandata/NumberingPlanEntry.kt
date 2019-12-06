package com.wavywalk.e164standarttringformatter.numberingplandata

class NumberingPlanEntry(
        val nationalDestinationCode: Array<Int>,
        val minLength: Int,
        val maxLength: Int,
        val nationalDestinationCodeType: String,
        val nationalDestinationCodeTypeSpecifier: String
)