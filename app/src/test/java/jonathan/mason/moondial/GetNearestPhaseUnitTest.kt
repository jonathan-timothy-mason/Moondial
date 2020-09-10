package jonathan.mason.moondial

import org.junit.Test
import org.junit.Assert.*
import org.junit.runners.Parameterized
import org.junit.runner.RunWith

/**
 * Test "getNearestPhase" of "Phases" enum; that expected phases are returned
 * for various days.
 *
 * Test cases from "Parameterized JUnit4 test example in Kotlin" by Ross Harper:
 * https://gist.github.com/rossharper/8f6c3c169b6b5c23e12c
 */
@RunWith(Parameterized::class)
class GetNearestPhaseUnitTest(private val day: Double, private val expectedPhase: Phases) {
    /**
     * Test cases.
     *
     * Days are rounded to 2 decimal places, otherwise, if following values exactly,
     * floating point errors can interfere.
     *
     * 29.53 / 16 = 1.845625
     * 1.845625 / 2 = 0.9228125
     *
     * Day
     * 0     New Moon
     *  <--0.5
     * 1.0   Day Old)
     *  <--1.4228125
     * 1.845625 Waxing Crescent Thin
     *  <--2.7684375
     * 3.69125  Waxing Crescent
     *  <--4.6140625
     * 5.536875  Waxing Crescent Thick
     *  <--6.4596875
     * 7.3825  First Quarter
     *  <--8.3053125
     * 9.228125  Waxing Gibbous Thin
     *  <--10.1509375
     * 11.07375 Waxing Gibbous
     *  <--11.9965625
     * 12.919375 Waxing Gibbous Thick
     *  <--13.8421875
     * 14.765 Full Moon
     *  <--15.6878125
     * 16.610625 Waning Gibbous Thick
     *  <--17.5334375
     * 18.45625 Waning Gibbous
     *  <--19.3790625
     * 20.301875 Waning Gibbous Thin
     *  <--21.2246875
     * 22.1475 Last Quarter
     *  <--23.0703125
     * 23.993125 Waning Crescent Thick
     *  <--24.9159375
     * 25.83875 Waning Crescent
     *  <--26.7615625
     * 27.684375 Waning Crescent Thin
     *  <--28.6071875
     * 29.53 New Moon
     */
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun testCases() = listOf(
            // New Moon.
            arrayOf(0.00, Phases.NewMoon),
            arrayOf(0.49, Phases.NewMoon),
            arrayOf(0.50, Phases.NewMoon),

            // Day Old.
            arrayOf(0.51, Phases.DayOld),
            arrayOf(1.00, Phases.DayOld),
            arrayOf(1.42, Phases.DayOld),

            // Waxing Crescent Thin.
            arrayOf(1.43, Phases.WaxingCrescentThin),
            arrayOf(1.85, Phases.WaxingCrescentThin),
            arrayOf(2.76, Phases.WaxingCrescentThin),

            // Waxing Crescent.
            arrayOf(2.77, Phases.WaxingCrescent),
            arrayOf(3.69, Phases.WaxingCrescent),
            arrayOf(4.61, Phases.WaxingCrescent),

            // Waxing Crescent Thick.
            arrayOf(4.62, Phases.WaxingCrescentThick),
            arrayOf(5.54, Phases.WaxingCrescentThick),
            arrayOf(6.45, Phases.WaxingCrescentThick),

            // First Quarter.
            arrayOf(6.46, Phases.FirstQuarter),
            arrayOf(7.38, Phases.FirstQuarter),
            arrayOf(8.30, Phases.FirstQuarter),

            // Waxing Gibbous Thin.
            arrayOf(8.31, Phases.WaxingGibbousThin),
            arrayOf(9.23, Phases.WaxingGibbousThin),
            arrayOf(10.15, Phases.WaxingGibbousThin),

            // Waxing Gibbous.
            arrayOf(10.16, Phases.WaxingGibbous),
            arrayOf(11.07, Phases.WaxingGibbous),
            arrayOf(11.99, Phases.WaxingGibbous),

            // Waxing Gibbous Thick.
            arrayOf(12.00, Phases.WaxingGibbousThick),
            arrayOf(12.92, Phases.WaxingGibbousThick),
            arrayOf(13.84, Phases.WaxingGibbousThick),

            // Full Moon.
            arrayOf(13.85, Phases.FullMoon),
            arrayOf(14.77, Phases.FullMoon),
            arrayOf(15.68, Phases.FullMoon),

            // Waning Gibbous Thick.
            arrayOf(15.69, Phases.WaningGibbousThick),
            arrayOf(16.61, Phases.WaningGibbousThick),
            arrayOf(17.53, Phases.WaningGibbousThick),

            // Waning Gibbous.
            arrayOf(17.54, Phases.WaningGibbous),
            arrayOf(18.46, Phases.WaningGibbous),
            arrayOf(19.37, Phases.WaningGibbous),

            // Waning Gibbous Thin.
            arrayOf(19.38, Phases.WaningGibbousThin),
            arrayOf(20.30, Phases.WaningGibbousThin),
            arrayOf(21.22, Phases.WaningGibbousThin),

            // Last Quarter.
            arrayOf(21.23, Phases.LastQuarter),
            arrayOf(22.15, Phases.LastQuarter),
            arrayOf(23.07, Phases.LastQuarter),

            // Waning Crescent Thick.
            arrayOf(23.08, Phases.WaningCrescentThick),
            arrayOf(23.99, Phases.WaningCrescentThick),
            arrayOf(24.91, Phases.WaningCrescentThick),

            // Waning Crescent.
            arrayOf(24.92, Phases.WaningCrescent),
            arrayOf(25.84, Phases.WaningCrescent),
            arrayOf(26.76, Phases.WaningCrescent),

            // Waning Crescent Thin.
            arrayOf(26.77, Phases.WaningCrescentThin),
            arrayOf(27.68, Phases.WaningCrescentThin),
            arrayOf(28.60, Phases.WaningCrescentThin),

            // New Moon.
            arrayOf(28.61, Phases.NewMoon),
            arrayOf(29.53, Phases.NewMoon)
        )
    }

    /**
     * Actual test.
     */
    @Test
    fun getNearestPhaseUnitTest() {
        val actualPhase = Phases.getNearestPhase(day)
        assertEquals("Incorrect phase for specified day ${ day }.", expectedPhase, actualPhase)
    }
}
