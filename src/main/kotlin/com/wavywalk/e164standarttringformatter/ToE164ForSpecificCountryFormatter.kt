package com.wavywalk.e164standarttringformatter

import com.wavywalk.e164standarttringformatter.exceptions.NoSuchDestinationCodeExitsInNumberingPlan
import com.wavywalk.e164standarttringformatter.exceptions.NumberSizeIsNotCompliantWithNumberingPlan
import com.wavywalk.e164standarttringformatter.exceptions.ResultPhoneNumberExceedsAllowedLength
import com.wavywalk.e164standarttringformatter.numberingplandata.CountryData
import com.wavywalk.e164standarttringformatter.numberingplandata.DistinctCountryNumberingPlanTree
import com.wavywalk.e164standarttringformatter.numberingplandata.NumberingPlanEntry
import com.wavywalk.e164standarttringformatter.utilities.DigitCharsPlucker
import java.lang.IllegalStateException
import java.lang.StringBuilder

class ToE164ForSpecificCountryFormatter(
        val inputString: String,
        val countryData: CountryData
) {

    val digits = mutableListOf<Int>()

    var digitCursor = 0

    val result = StringBuilder()

    var resultNumberingPlanEntry: NumberingPlanEntry? = null

    init {
        countryData.countryCode.forEach {
            result.append(it)
        }
    }

    fun format(): String {
        DigitCharsPlucker.pluckIntoList(inputString, digits)

        if (startsWithExitCodeFollowedByCountryCode()) {
            digitCursor += countryData.exitCodeWithCountryCode.size
        } else if (startsWithCountryCode()) {
            digitCursor += countryData.countryCode.size
        }
        if (startsWithTrunk(digitCursor)) {
            digitCursor += countryData.trunk.size
        }
        pluckDestination()
        pluckSubsciberNumber()
        val result = result.toString()
        validateResult(result)
        return(result)
    }

    fun validateResult(result: String) {
        if ((result.length-2) > resultNumberingPlanEntry!!.maxLength) {
            throw NumberSizeIsNotCompliantWithNumberingPlan(result,countryData,resultNumberingPlanEntry!!)
        }
        if ((result.length-2) < resultNumberingPlanEntry!!.minLength) {
            throw NumberSizeIsNotCompliantWithNumberingPlan(result,countryData,resultNumberingPlanEntry!!)
        }
    }

    @Throws
    private fun pluckDestination(): NumberingPlanEntry? {
        var currentNode: DistinctCountryNumberingPlanTree? = countryData.numberingPlanTree!!

        while (digitCursor < digits.size && currentNode != null) {
            val value = digits[digitCursor]
            result.append(value)
            if (currentNode.numberingPlanEntry != null) {
                resultNumberingPlanEntry = currentNode.numberingPlanEntry
                digitCursor += 1
                return resultNumberingPlanEntry
            }
            currentNode = currentNode.branches[digits[digitCursor]]
            digitCursor += 1
        }
        throw NoSuchDestinationCodeExitsInNumberingPlan(countryData, inputString)
    }

    private fun pluckSubsciberNumber() {
        while (digitCursor< digits.size) {
            result.append(digits[digitCursor])
            digitCursor += 1
        }
    }

    private fun startsWithExitCodeFollowedByCountryCode(): Boolean {
        val targetSublist = digits.subList(0, countryData.exitCodeWithCountryCode.size)
        countryData.exitCodeWithCountryCode.forEachIndexed {index, value ->
            if (value != targetSublist[index]) {
                return false
            }
        }
        return true
    }

    private fun startsWithTrunk(startIndex: Int = 0): Boolean {
        val targetSublist = digits.subList(startIndex, startIndex + countryData.trunk.size)
        countryData.trunk.forEachIndexed { index, value ->
            if (value != targetSublist[index]) {
                return false
            }
        }
        return true
    }

    private fun startsWithCountryCode(): Boolean {
        val targetSublist = digits.subList(0, countryData.countryCode.size)
        countryData.countryCode.forEachIndexed { index, value ->
            if (value != targetSublist[index]) {
                return false
            }
        }
        return true
    }

}