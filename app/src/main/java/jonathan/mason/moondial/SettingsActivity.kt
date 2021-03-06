package jonathan.mason.moondial

import android.appwidget.AppWidgetManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

/**
 * Settings screen, automatically generated by Android Studio template.
 */
class SettingsActivity : AppCompatActivity() {

    /**
     * Perform initialisation of activity.
     * Saved state of activity [savedInstanceState] is unused.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Shared preferences fragment, automatically generated by Android Studio template.
     */
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    /**
     * Override to update widgets according to changed shared preferences.
     */
    override fun onDestroy() {
        super.onDestroy()

        MoondialWidget.updateAllAppWidgets(this.applicationContext, AppWidgetManager.getInstance(this.applicationContext))
    }
}