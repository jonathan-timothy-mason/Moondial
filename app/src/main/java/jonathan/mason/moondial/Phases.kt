package jonathan.mason.moondial

import android.text.format.Time
import java.util.*

const val LUNAR_PERIOD = 29.53
const val KNOWN_NEW_MOON_JULIAN_DATE = 2451549.5 // 6th January 2000.

/**
 * Phases of moon.
 *
 * Each phase has IDs corresponding to image [drawable] and string resource [stringName],
 * as well as number of days in lunar period at which it is visible.
 */
enum class Phases(val drawable: Int, val stringName: Int, val day: Double) {
    NewMoon(R.drawable.new_moon, R.string.new_moon, 0.0),
    DayOld(R.drawable.day_old, R.string.day_old, 1.0),
    WaxingCrescentThin(R.drawable.waxing_crescent_thin, R.string.waxing_crescent_thin, LUNAR_PERIOD * 1/16),
    WaxingCrescent(R.drawable.waxing_crescent, R.string.waxing_crescent, LUNAR_PERIOD * 1/8),
    WaxingCrescentThick(R.drawable.waxing_crescent_thick, R.string.waxing_crescent_thick, LUNAR_PERIOD * 3/16),
    FirstQuarter(R.drawable.first_quarter, R.string.first_quarter, LUNAR_PERIOD * 1/4),
    WaxingGibbousThin(R.drawable.waxing_gibbous_thin, R.string.waxing_gibbous_thin, LUNAR_PERIOD * 5/16),
    WaxingGibbous(R.drawable.waxing_gibbous, R.string.waxing_gibbous, LUNAR_PERIOD * 3/8),
    WaxingGibbousThick(R.drawable.waxing_gibbous_thick, R.string.waxing_gibbous_thick, LUNAR_PERIOD * 7/16),
    FullMoon(R.drawable.full_moon, R.string.full_moon, LUNAR_PERIOD * 1/2),
    WaningGibbousThick(R.drawable.waning_gibbous_thick, R.string.waning_gibbous_thick, LUNAR_PERIOD * 9/16),
    WaningGibbous(R.drawable.waning_gibbous, R.string.waning_gibbous, LUNAR_PERIOD * 5/8),
    WaningGibbousThin(R.drawable.waning_gibbous_thin, R.string.waning_gibbous_thin, LUNAR_PERIOD * 11/16),
    LastQuarter(R.drawable.last_quarter, R.string.last_quarter, LUNAR_PERIOD * 3/4),
    WaningCrescentThick(R.drawable.waning_crescent_thick, R.string.waning_crescent_thick, LUNAR_PERIOD * 13/16),
    WaningCrescent(R.drawable.waning_crescent, R.string.waning_crescent, LUNAR_PERIOD * 7/8),
    WaningCrescentThin(R.drawable.waning_crescent_thin, R.string.waning_crescent_thin, LUNAR_PERIOD * 15/16);

    companion object {
        /**
         * Get phase of moon following phase specified by [currentPhase].
         */
        fun getNextPhase(currentPhase: Phases): Phases {
            return values().filter { p -> p.ordinal == currentPhase.ordinal + 1 }.firstOrNull() ?: values()[0]
        }

        /**
         * Get phase of moon most closely corresponding to supplied day [currentDay]
         * in lunar period.
         */
        fun getNearestPhase(currentDay: Double): Phases {
            // Use Pair to associate Phase and its diff.
            // Initialise to case not handled by looping through enum values, where
            // currentDay may be a large number near the end of the lunar period, but
            // actually nearer to New Moon (0).
            var nearestPhase = NewMoon to Math.abs(LUNAR_PERIOD - currentDay)

            // Loop to see if another phase is closer to the current day.
            for(p in values()) {
                val diff = Math.abs(p.day- currentDay)
                if(diff < nearestPhase.second)
                    nearestPhase = p to diff
            }

            return nearestPhase.first
        }

        /**
         * Get current phase of moon.
         * From "Calculate the Moon Phase" by SubsySTEMs:
         * https://www.subsystems.us/uploads/9/8/9/4/98948044/moonphase.pdf.
         */
        fun calculateCurrentPhase(): Phases {
            val now = Date()

            // From answer to "How to get UTC offset in seconds in android" by Dan S:
            // https://stackoverflow.com/questions/7289660/how-to-get-utc-offset-in-seconds-in-android.
            val offset = TimeZone.getDefault().getOffset(now.time) / 1000

            // From "Calculate the Moon Phase" by SubsySTEMs:
            // https://www.subsystems.us/uploads/9/8/9/4/98948044/moonphase.pdf.
            val currentJulianDate = Time.getJulianDay(now.time, offset.toLong())
            val daysSinceNew = currentJulianDate - KNOWN_NEW_MOON_JULIAN_DATE
            val daysSinceNewMoon = daysSinceNew % LUNAR_PERIOD

            return this.getNearestPhase(daysSinceNewMoon)
        }
    }
}