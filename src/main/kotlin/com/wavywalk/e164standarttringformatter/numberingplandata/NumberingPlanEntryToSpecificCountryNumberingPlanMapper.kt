package com.wavywalk.e164standarttringformatter.numberingplandata

import java.lang.IllegalStateException

class NumberingPlanEntryToSpecificCountryNumberingPlanMapper(
        private val countryData: CountryData,
        private val numberingPlanEntries: MutableList<NumberingPlanEntry>
) {

    fun mapEntries() {
        countryData.numberingPlanTree = DistinctCountryNumberingPlanTree()

        numberingPlanEntries.forEach {
            mapEntry(countryData.numberingPlanTree!!, it)
        }
    }

    private fun mapEntry(treeRoot: DistinctCountryNumberingPlanTree, entry: NumberingPlanEntry) {
        var currentNode = treeRoot

        entry.nationalDestinationCode.forEach {
            val nextNode =  currentNode.branches[it] ?: DistinctCountryNumberingPlanTree()
            currentNode.branches[it] = nextNode
            currentNode = nextNode
        }

        if (currentNode.numberingPlanEntry != null) {
            throw IllegalStateException("Duplicate entry")
        }

        currentNode.numberingPlanEntry = entry
    }

}