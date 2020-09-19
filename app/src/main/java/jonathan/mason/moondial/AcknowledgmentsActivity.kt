package jonathan.mason.moondial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Acknowledgments screen of app, acknowledging full moon photo.
 */
class AcknowledgmentsActivity : AppCompatActivity() {
    /**
     * Perform initialisation of activity.
     * Saved state of activity [savedInstanceState] is unused.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acknowledgments)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Make sky same as MainActivity
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val sky = Skies.fromPrefs(this, sharedPrefs.getString(getString(R.string.sky_key), getString(R.string.sky_value_default)))
        val constraintLayout = this.findViewById(R.id.constraintLayout) as ConstraintLayout
        constraintLayout.setBackgroundResource(sky.drawable)
    }
}
