package com.wavywalk.e164standarttringformatter.numberingplandata

class StartingWithCountryCodeNodesTree {

    companion object {
        val root = StartingWithCountryCodeNodesTree()
    }

    var branches: Array<StartingWithCountryCodeNodesTree?> = Array<StartingWithCountryCodeNodesTree?>(10) {
        null
    }

    var isDeadEnd = true

    var countriesOnThisNode: MutableList<CountryData>? = null

    fun getOrInitCountriesOnThisNode(): MutableList<CountryData> {
        if (countriesOnThisNode == null) {
            countriesOnThisNode = mutableListOf()
        }
        return countriesOnThisNode!!
    }

    fun getNodeOnBranchOrInitialize(index: Int): StartingWithCountryCodeNodesTree {
        if (branches[index] == null) {
            branches[index] = StartingWithCountryCodeNodesTree()
        }
        return branches[index]!!
    }


}