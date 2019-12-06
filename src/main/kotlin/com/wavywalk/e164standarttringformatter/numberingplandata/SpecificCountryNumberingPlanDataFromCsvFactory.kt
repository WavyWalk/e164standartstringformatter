package com.wavywalk.e164standarttringformatter.numberingplandata

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.io.BufferedInputStream
import java.lang.IllegalStateException

class SpecificCountryNumberingPlanDataFromCsvFactory(
        val countryData: CountryData
) {


    fun build(csvResourcePath: String): MutableList<NumberingPlanEntry> {
        val parserResult = parseCsv(csvResourcePath)!!

        val entriesToReturn = mutableListOf<NumberingPlanEntry>()

        parserResult.records?.forEach {
            val destinationCode = it[0].map {
                it - '0'
            }.toTypedArray()

            val maxLength = Integer.parseInt(it[1])
            val minLength = Integer.parseInt(it[2])
            val nationalDestinationCodeType = it[3]
            val nationalDestinationCodeTypeSpecifier = it[4]

            val entry = NumberingPlanEntry(
                    destinationCode,
                    minLength,
                    maxLength,
                    nationalDestinationCodeType,
                    nationalDestinationCodeTypeSpecifier
            )

            entriesToReturn.add(entry)
        }
        return entriesToReturn
    }

    private fun parseCsv(csvResourcePath: String): CSVParser? {
        val reader = javaClass.getResourceAsStream(csvResourcePath).reader()
        return CSVParser.parse(reader, CSVFormat.DEFAULT)
    }


}