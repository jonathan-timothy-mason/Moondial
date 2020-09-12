package jonathan.mason.moondial

import org.junit.Test
import org.junit.Assert.*
import org.junit.runners.Parameterized
import org.junit.runner.RunWith
import java.util.*

/**
 * Test "calculateCurrentDays" of "Phases" enum; that expected days [expectedDays] is
 * returned for each date [date].
 *
 * Test cases from "Parameterized JUnit4 test example in Kotlin" by Ross Harper:
 * https://gist.github.com/rossharper/8f6c3c169b6b5c23e12c
 */
@RunWith(Parameterized::class)
class CalculateCurrentDaysUnitTest(private val date: Date, private val expectedDays: Double) {
    /**
     * Test cases.
     *
     * Times of moon phases from "Phases of the Moon: 1901 to 2000" by Fred Espenak:
     * http://astropixels.com/ephemeris/phasescat/phases2001.html.
     */
    companion object {
        private val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        private lateinit var newMoon1: Date
        private lateinit var firstQuarter1: Date
        private lateinit var fullMoon1: Date
        private lateinit var lastQuarter1: Date
        private lateinit var nextNewMoon1: Date
        private lateinit var newMoon2: Date
        private lateinit var firstQuarter2: Date
        private lateinit var fullMoon2: Date
        private lateinit var lastQuarter2: Date
        private lateinit var nextNewMoon2: Date

        init {
            // New Moon.
            calendar.set(2020, 0, 24, 21, 42)
            newMoon1 = calendar.time

            // First Quarter.
            calendar.set(2020, 1, 2, 1, 42)
            firstQuarter1 = calendar.time

            // Full Moon.
            calendar.set(2020, 1, 9, 7, 33)
            fullMoon1 = calendar.time

            // Last Quarter.
            calendar.set(2020, 1, 15, 22, 17)
            lastQuarter1 = calendar.time

            // Next New Moon.
            calendar.set(2020, 1, 23, 15, 32)
            nextNewMoon1 = calendar.time

            // New Moon.
            calendar.set(2100, 0, 10, 12, 57)
            newMoon2 = calendar.time

            // First Quarter.
            calendar.set(2100, 0, 18, 12, 35)
            firstQuarter2 = calendar.time

            // Full Moon.
            calendar.set(2100, 0, 26, 2, 51)
            fullMoon2 = calendar.time

            // Last Quarter.
            calendar.set(2100, 1, 1, 21, 17)
            lastQuarter2 = calendar.time

            // Next New Moon.
            calendar.set(2100, 1, 9, 4, 56)
            nextNewMoon2 = calendar.time
        }

        @JvmStatic
        @Parameterized.Parameters
        fun testCases() = listOf(
                arrayOf(newMoon1, 0.0),
                arrayOf(firstQuarter1, 7.38),
                arrayOf(fullMoon1, 14.77),
                arrayOf(lastQuarter1, 22.15),
                arrayOf(nextNewMoon1, 0.0),
                arrayOf(newMoon2, 0.0),
                arrayOf(firstQuarter2, 7.38),
                arrayOf(fullMoon2, 14.77),
                arrayOf(lastQuarter2, 22.15),
                arrayOf(nextNewMoon2, 0.0)
        )
    }

    /**
     * Actual test.
     */
    @Test
    fun calculateCurrentDaysUnitTest() {
        val actualDays = Phases.calculateCurrentDays(date)
        assertEquals("Incorrect days for specified date ${ date }.", expectedDays, actualDays, 1.0)
    }
}