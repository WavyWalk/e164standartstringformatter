package com.wavywalk.e164standarttringformatter

import com.wavywalk.e164standarttringformatter.numberingplandata.CountriesCountryCodesExitCodesAndTrunksTreeBuilder
import com.wavywalk.e164standarttringformatter.numberingplandata.CountryData
import com.wavywalk.e164standarttringformatter.numberingplandata.NumberingPlanEntryToSpecificCountryNumberingPlanMapper
import com.wavywalk.e164standarttringformatter.numberingplandata.SpecificCountryNumberingPlanDataFromCsvFactory
import com.wavywalk.e164standarttringformatter.utilities.ApplicationUserInputListener

object Application {

    lateinit var germanyData: CountryData

    fun prepare() {
        prepareInternationalFormatter()
        prepareGermanFormatter()
    }

    private fun prepareInternationalFormatter() {
        CountriesCountryCodesExitCodesAndTrunksTreeBuilder.buildTrees()
    }

    private fun prepareGermanFormatter() {
        val germany = CountryData(
            name ="Germany",
            countryCode = arrayOf(4,9),
            exitCode = arrayOf(0, 0),
            trunk = arrayOf(0)
        )
        val numberingPlanEntries = SpecificCountryNumberingPlanDataFromCsvFactory(germany).build("/germany_numbering_plan.csv")
        NumberingPlanEntryToSpecificCountryNumberingPlanMapper(germany, numberingPlanEntries).mapEntries()
        germanyData = germany
    }

    fun run() {
        prepare()
        ApplicationUserInputListener.run()
    }

}