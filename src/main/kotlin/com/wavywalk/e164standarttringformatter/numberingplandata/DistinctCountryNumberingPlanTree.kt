package com.wavywalk.e164standarttringformatter.numberingplandata

class DistinctCountryNumberingPlanTree {

    var numberingPlanEntry: NumberingPlanEntry? = null

    val branches = Array<DistinctCountryNumberingPlanTree?>(10) {
        null
    }

    fun getNodeOnBranchOrInitialize(index: Int): DistinctCountryNumberingPlanTree {
        if (branches[index] == null) {
            branches[index] = DistinctCountryNumberingPlanTree()
        }
        return branches[index]!!
    }

}