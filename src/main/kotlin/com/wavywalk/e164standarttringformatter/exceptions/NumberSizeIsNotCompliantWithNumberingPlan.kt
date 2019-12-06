package com.wavywalk.e164standarttringformatter.exceptions

import com.wavywalk.e164standarttringformatter.numberingplandata.CountryData
import com.wavywalk.e164standarttringformatter.numberingplandata.NumberingPlanEntry
import java.lang.Exception

class NumberSizeIsNotCompliantWithNumberingPlan(
        number: String,
        countryData: CountryData,
        numberingPlanEntry: NumberingPlanEntry
) : Exception() {
    override val message: String?

    init {
        message = """
            Invalid. number's length is ${number.length - 2} is not allowed by numbering plang
            should be in >= ${numberingPlanEntry.minLength} and <= ${numberingPlanEntry.maxLength}
        """
    }
}