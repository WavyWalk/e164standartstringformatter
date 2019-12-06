package com.wavywalk.e164standarttringformatter.numberingplandata

class CountriesCountryCodesExitCodesAndTrunksTreeBuilder {

    companion object {

        fun buildTrees() {
            val countriesCountryCodesExitCodesAndTrunksData = CountriesCountryCodesExitCodesAndTrunksData

            val treeBuilder = CountriesCountryCodesExitCodesAndTrunksTreeBuilder()

            countriesCountryCodesExitCodesAndTrunksData.data.forEach {
                val countryName = it.get(0)

                val countryCode = it.get(1).map {
                    it - '0'
                }.toTypedArray()

                val exitCode = it.get(2).map {
                    it - '0'
                }.toTypedArray()

                val trunk = it.get(3).map {
                    it - '0'
                }.toTypedArray()

                val countryData = CountryData(
                        countryName,
                        countryCode,
                        trunk,
                        exitCode
                )

                treeBuilder.mapToStartingWithCountryCodeNodesTree(countryData)
                treeBuilder.mapToExitCodeFollowedByCountryCodeTree(countryData)

            }
        }
    }

    fun mapToStartingWithCountryCodeNodesTree(country: CountryData) {
        var currentNode = StartingWithCountryCodeNodesTree.root
        currentNode.isDeadEnd = false

        country.countryCode.forEach {
            currentNode = currentNode.getNodeOnBranchOrInitialize(it)
            currentNode.isDeadEnd = false
        }

        currentNode.getOrInitCountriesOnThisNode().add(country)
    }

    fun mapToExitCodeFollowedByCountryCodeTree(country: CountryData) {
        var currentnode = ExitCodeFollowedByCountryCodeTree.root
        currentnode.isDeadEnd = false

        country.exitCodeWithCountryCode.forEach {
            currentnode = currentnode.getNodeOnBranchOrInitialize(it)
            currentnode.isDeadEnd = false
        }

        currentnode.getOrInitCountriesOnThisNode().add(country)
    }

}