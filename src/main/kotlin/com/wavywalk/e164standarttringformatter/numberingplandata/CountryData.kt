package com.wavywalk.e164standarttringformatter.numberingplandata

class CountryData(
        val name: String,
        val countryCode: Array<Int>,
        val trunk: Array<Int>,
        val exitCode: Array<Int>
) {

    val exitCodeWithCountryCode: Array<Int>

    init {
        exitCodeWithCountryCode = exitCode + countryCode
    }

    var numberingPlanTree: DistinctCountryNumberingPlanTree? = null

}