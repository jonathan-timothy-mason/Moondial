package jonathan.mason.moondial

import java.util.*

const val LUNAR_PERIOD = 29.53

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
         * Get current phase of moon.
         *
         * According to "Set the Time Zone of a Date in Java" by baeldung, Date() returns the
         * current date and time in GMT, irrespective of time zone:
         * https://www.baeldung.com/java-set-date-time-zone.
         */
        fun calculateCurrentPhase(): Phases {
            return this.getNearestPhase(calculateCurrentDays(Date()))
        }

        /**
         * Calculate number of days into lunar period for date specified by [date].
         *
         * Based on "Calculate the Moon Phase" by SubsySTEMs:
         * https://www.subsystems.us/uploads/9/8/9/4/98948044/moonphase.pdf.
         *
         * Time of known new moon from "Phases of the Moon: 1901 to 2000" by Fred Espenak:
         * http://astropixels.com/ephemeris/phasescat/phases2001.html.
         */
        internal fun calculateCurrentDays(date: Date): Double {
            // Get time of known new moon.
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
            calendar.set(2020, 0, 24, 21, 42)
            val knownNewMoonDate = calendar.time

            // Get difference between date and known new moon as days.
            val daysSinceKnownNewMoon =  (date.time - knownNewMoonDate.time) / (1000 * 60 * 60 * 24)

            // Remove complete periods to leave remainder, the number of days into the
            // current lunar period.
            return daysSinceKnownNewMoon % LUNAR_PERIOD
        }

        /**
         * Get phase of moon most closely corresponding to supplied day [currentDay]
         * in lunar period.
         */
        internal fun getNearestPhase(currentDay: Double): Phases {
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
    }
}