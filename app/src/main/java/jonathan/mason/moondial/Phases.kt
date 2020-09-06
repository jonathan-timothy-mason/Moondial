package jonathan.mason.moondial

import android.text.format.Time
import java.util.*
import java.util.Calendar.DAY_OF_YEAR


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
            return values().filter { p -> p.ordinal == currentPhase.ordinal + 1 }.firstOrNull() ?: values()[0]
        }

        /**
         * From "Calculate the Moon Phase" by SubsySTEMs:
         * https://www.subsystems.us/uploads/9/8/9/4/98948044/moonphase.pdf.
         */
        fun calculateCurrentPhase(): Phases {
            val jd = Time.getJulianDay(Date().time, 0)
            val daysSinceNew = jd - 2451549.5
            val daysSinceNewMoon = daysSinceNew % 29.53

            return when {
                daysSinceNewMoon in 1.0..3.0 -> WaxingCrescentThin // 2
                daysSinceNewMoon in 3.0..5.0 -> WaxingCrescent // 4
                daysSinceNewMoon in 5.0..7.0 -> WaxingCrescentThick  //6
                daysSinceNewMoon in 7.0..9.0 -> FirstQuarter // 8
                daysSinceNewMoon in 9.0..11.0 -> WaxingGibbousThin  // 10
                daysSinceNewMoon in 11.0..13.0 -> WaxingGibbous  //12
                daysSinceNewMoon in 13.0..15.0 -> WaxingGibbousThick //14
                daysSinceNewMoon in 15.0..17.0 -> Full // 16
                daysSinceNewMoon in 17.0..19.0 -> WaningGibbousThick
                daysSinceNewMoon in 19.0..21.0 -> WaningGibbous
                daysSinceNewMoon in 21.0..23.0 -> WaningGibbousThin
                daysSinceNewMoon in 23.0..25.0 -> LastQuarter
                daysSinceNewMoon in 25.0..27.0 -> WaningCrescentThick
                daysSinceNewMoon in 27.0..29.0 -> WaningCrescent
                daysSinceNewMoon in 29.0..31.0 -> WaningCrescentThin
                else -> NewNoCrescent
            }
        }
    }
}