package jonathan.mason.moondial

import android.content.Context

/**
 * Appearances of sky.
 *
 * Each sky has IDs corresponding to gradients [drawable] and [roundedDrawable].
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

        /**
         * Convert shared preferences sky value [prefsValue] to corresponding "Skies"
         * enumeration value.
         */
        fun fromPrefs(context: Context, prefsValue: String?): Skies {
            return when(prefsValue) {
                context.getString(R.string.sky_value_day_light) -> DayLight
                context.getString(R.string.sky_value_day_dark) -> DayDark
                context.getString(R.string.sky_value_twilight_light) -> TwighlightLight
                context.getString(R.string.sky_value_twilight_dark) -> TwighlightDark
                context.getString(R.string.sky_value_night_light) -> NightLight
                else -> NightDark
            }
        }

        /**
         * Convert "Skies" enumeration value to corresponding shared preferences sky
         * value.
         */
        fun toPrefs(context: Context, sky: Skies): String {
            return when(sky) {
                DayLight -> context.getString(R.string.sky_value_day_light)
                DayDark -> context.getString(R.string.sky_value_day_dark)
                TwighlightLight -> context.getString(R.string.sky_value_twilight_light)
                TwighlightDark -> context.getString(R.string.sky_value_twilight_dark)
                NightLight -> context.getString(R.string.sky_value_night_light)
                NightDark -> context.getString(R.string.sky_value_night_dark)
            }
        }
    }
}