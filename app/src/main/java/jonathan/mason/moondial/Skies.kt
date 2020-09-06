package jonathan.mason.moondial

/**
 * Types of sky.
 */
enum class Skies(val drawable: Int) {
    Day(R.drawable.day),
    TwighlightLight(R.drawable.twilight_light),
    TwighlightDark(R.drawable.twilight_dark),
    NightLight(R.drawable.night_light),
    NightDark(R.drawable.night_dark);

    companion object {
        /**
         * Get next type of sky.
         */
        fun getNextSky(currentSky: Skies): Skies {
            return values().filter { p -> p.ordinal == currentSky.ordinal + 1 }.firstOrNull() ?: Day
        }
    }
}