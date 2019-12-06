package com.wavywalk.e164standarttringformatter.numberingplandata

class ExitCodeFollowedByCountryCodeTree() {

    companion object {
        val root = ExitCodeFollowedByCountryCodeTree()
    }

    var countriesOnThisNode: MutableList<CountryData>? = null

    var isDeadEnd = true

    val branches = Array<ExitCodeFollowedByCountryCodeTree?>(10) {
        null
    }

    fun getOrInitCountriesOnThisNode(): MutableList<CountryData> {
        if (countriesOnThisNode == null) {
            countriesOnThisNode = mutableListOf()
        }
        return countriesOnThisNode!!
    }

    fun getNodeOnBranchOrInitialize(index: Int): ExitCodeFollowedByCountryCodeTree {
        if (branches[index] == null) {
            branches[index] = ExitCodeFollowedByCountryCodeTree()
        }
        return branches[index]!!
    }

}