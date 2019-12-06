package com.wavywalk.e164standarttringformatter

import com.wavywalk.e164standarttringformatter.exceptions.ResultPhoneNumberExceedsAllowedLength
import com.wavywalk.e164standarttringformatter.numberingplandata.CountryData
import com.wavywalk.e164standarttringformatter.numberingplandata.ExitCodeFollowedByCountryCodeTree
import com.wavywalk.e164standarttringformatter.numberingplandata.StartingWithCountryCodeNodesTree
import com.wavywalk.e164standarttringformatter.utilities.DigitCharsPlucker
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.StringBuilder


class ToE164AllowingExitCodePrefixFormatter(inputString: String) {

    val digitList: MutableList<Int>

    val recordedErrors = mutableListOf<Exception>()

    init {
        digitList = mutableListOf()
        DigitCharsPlucker.pluckIntoList(inputString, digitList)

    }

    private val intermediaryResultStringBuilder = StringBuilder()

    private val resultByCountry = mutableMapOf<String, String>()

    fun extract(): MutableMap<String, String> {
        if (digitList.size < 3) {
            recordedErrors.add(IllegalStateException("invalid input"))
            return resultByCountry
        }

        val candidateCountriesWithExitCodePrefix = startsWithExitFollowedByCountryCode()

        if (candidateCountriesWithExitCodePrefix !== null) {
            candidateCountriesWithExitCodePrefix.forEach {
                pluckResultStartingWith(it.countryCode, it, it.exitCodeWithCountryCode.size)
            }
            return resultByCountry
        }

        startsWithCountryCode()?.forEach {
            pluckResultStartingWith(it.countryCode, it, it.countryCode.size)
        }

        return resultByCountry
    }


    private fun pluckResultStartingWith(
            startArray: Array<Int>,
            countryData: CountryData,
            firstAfterCountryCodeIndex: Int
    ) {
        startArray.forEach {
            intermediaryResultStringBuilder.append(it)
        }
        pluckRestSkippingTrunkIfNecessary(firstAfterCountryCodeIndex, countryData)
        addResultEntry(countryData, intermediaryResultStringBuilder.toString())
        intermediaryResultStringBuilder.setLength(0)
    }

    private fun addResultEntry(country: CountryData, resultNumber: String) {
        if (resultNumber.length > 15) {
            recordedErrors.add(
                ResultPhoneNumberExceedsAllowedLength(country, resultNumber)
            )
            return
        }
        resultByCountry[country.name] = resultNumber
    }

    private fun pluckRestSkippingTrunkIfNecessary(digitListStartCursor: Int, countryData: CountryData) {
        var digitListCursor = digitListStartCursor
        countryData.trunk.forEach {
            if (digitList[digitListCursor] == it) {
                digitListCursor += 1
            }
        }
        while (digitListCursor < digitList.size) {
            intermediaryResultStringBuilder.append(digitList[digitListCursor])
            digitListCursor += 1
        }
    }

    private fun startsWithExitFollowedByCountryCode(): MutableList<CountryData>? {
        var node: ExitCodeFollowedByCountryCodeTree? = ExitCodeFollowedByCountryCodeTree.root
        var lastCandidateNode: ExitCodeFollowedByCountryCodeTree? = null
        for (index in 0 until (digitList.size)) {
            val digit = digitList[index]

            if (node!!.isDeadEnd) {
                return node.countriesOnThisNode
            }

            if (node.countriesOnThisNode !== null) {
                lastCandidateNode = node
            }

            node = node.branches[digit]
            node ?: return lastCandidateNode?.countriesOnThisNode
        }

        return lastCandidateNode?.countriesOnThisNode

    }

    private fun startsWithCountryCode(): MutableList<CountryData>? {
        var node: StartingWithCountryCodeNodesTree? = StartingWithCountryCodeNodesTree.root
        var lastCandidateNode: StartingWithCountryCodeNodesTree? = null
        for (index in 0..(digitList.size)) {
            val digit = digitList[index]

            if (node!!.isDeadEnd) {
                return node.countriesOnThisNode
            }

            if (node.countriesOnThisNode !== null) {
                lastCandidateNode = node
            }

            node = node.branches[digit]
            node ?: return lastCandidateNode?.countriesOnThisNode
        }

        return lastCandidateNode?.countriesOnThisNode

    }


}