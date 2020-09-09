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
            arrayOf(28.60718751, Phases.NewMoon),
            arrayOf(29.53, Phases.NewMoon),
            arrayOf(0, Phases.NewMoon),
            arrayOf(0.49, Phases.NewMoon),
            arrayOf(0.5, Phases.NewMoon),

            // Day Old.
            arrayOf(0.51, Phases.DayOld),
            arrayOf(1, Phases.DayOld),
            arrayOf(1.4228125, Phases.DayOld),

            // Waxing Crescent Thin.
            arrayOf(1.42281251, Phases.WaxingCrescentThin),
            arrayOf(1.845625, Phases.WaxingCrescentThin),
            arrayOf(2.7684375, Phases.WaxingCrescentThin),

            // Waxing Crescent.
            arrayOf(2.76843751, Phases.WaxingCrescent),
            arrayOf(3.69125, Phases.WaxingCrescent),
            arrayOf(4.6140625, Phases.WaxingCrescent),

            // Waxing Crescent Thick.
            arrayOf(4.61406251, Phases.WaxingCrescentThick),
            arrayOf(5.536875, Phases.WaxingCrescentThick),
            arrayOf(6.4596875, Phases.WaxingCrescentThick)
        )
    }

    /**
     * Actual test.
     */
    @Test
    fun getNearestPhaseUnitTest() {
        val actualPhase = Phases.getNearestPhase(day)
        assertEquals("Incorrect phase for specified day.", expectedPhase, actualPhase)
    }
}
