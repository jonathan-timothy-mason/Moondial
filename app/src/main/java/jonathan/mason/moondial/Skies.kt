package jonathan.mason.moondial

/**
 * Appearances of sky.
 */
enum class Skies(val drawable: Int) {
    DayLight(R.drawable.sky_day_light),
    DayDark(R.drawable.sky_day_dark),
    TwighlightLight(R.drawable.sky_twilight_light),
    TwighlightDark(R.drawable.sky_twilight_dark),
    NightLight(R.drawable.sky_night_light),
    NightDark(R.drawable.sky_night_dark);

    companion object {
        /**
         * Get next appearance of sky.
         */
        fun getNextSky(currentSky: Skies): Skies {
            return values().filter { p -> p.ordinal == currentSky.ordinal + 1 }.firstOrNull() ?: values()[0]
        }
    }
}