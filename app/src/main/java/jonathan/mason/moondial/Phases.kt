package jonathan.mason.moondial

/**
 * Phases of moon.
 */
enum class Phases(val drawable: Int, val string: Int) {
    NewNoCrescent(R.drawable.new_no_crescent, R.string.new_no_crescent),
    NewCrescent(R.drawable.new_crescent, R.string.new_crescent),
    WaxingCrescentThin(R.drawable.waxing_crescent_thin, R.string.waxing_crescent_thin),
    WaxingCrescent(R.drawable.waxing_crescent, R.string.waxing_crescent),
    WaxingCrescentThick(R.drawable.waxing_crescent_thick, R.string.waxing_crescent_thick),
    FirstQuarter(R.drawable.first_quarter, R.string.first_quarter),
    WaxingGibbousThin(R.drawable.waxing_gibbous_thin, R.string.waxing_gibbous_thin),
    WaxingGibbous(R.drawable.waxing_gibbous, R.string.waxing_gibbous),
    WaxingGibbousThick(R.drawable.waxing_gibbous_thick, R.string.waxing_gibbous_thick),
    Full(R.drawable.full, R.string.full),
    WaningGibbousThick(R.drawable.waning_gibbous_thick, R.string.waning_gibbous_thick),
    WaningGibbous(R.drawable.waning_gibbous, R.string.waning_gibbous),
    WaningGibbousThin(R.drawable.waning_gibbous_thin, R.string.waning_gibbous_thin),
    LastQuarter(R.drawable.last_quarter, R.string.last_quarter),
    WaningCrescentThick(R.drawable.waning_crescent_thick, R.string.waning_crescent_thick),
    WaningCrescent(R.drawable.waning_crescent, R.string.waning_crescent),
    WaningCrescentThin(R.drawable.waning_crescent_thin, R.string.waning_crescent_thin);

    companion object {
        /**
         * Get next phase of moon.
         */
        fun getNextPhase(currentPhase: Phases): Phases {
            return values().filter { p -> p.ordinal == currentPhase.ordinal + 1 }.firstOrNull() ?: NewNoCrescent
        }
    }
}