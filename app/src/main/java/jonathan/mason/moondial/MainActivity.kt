package jonathan.mason.moondial

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Todo:
 * Widget.
 * Allow image to be made smaller for watch.
 * Image descriptions.
 */

const val DISPLAY_PHASE = "DISPLAY_PHASE"

/**
 * Main screen of app, displaying moon and its phases.
 * Current phase of the moon resides in [currentPhase].
 */
class MainActivity(private val currentPhase: Phases = Phases.calculateCurrentPhase()) : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, View.OnLongClickListener {
    private var displayPhase = currentPhase
    private var displayPhaseChanged = false

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var imageViewForeground: ImageView
    private lateinit var imageViewBackground: ImageView
    private lateinit var textViewPhaseDescription: TextView

    /**
     * Perform initialisation of activity.
     * Saved state of activity [savedInstanceState] is unused.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        constraintLayout = this.findViewById(R.id.constraintLayout)
        imageViewForeground = this.findViewById(R.id.imageViewForeground)
        imageViewBackground = this.findViewById(R.id.imageViewBackground)
        textViewPhaseDescription =  this.findViewById(R.id.textViewPhaseDescription)

        // Allow long press anywhere to reset to current phase.
        constraintLayout.setOnLongClickListener(this)
        imageViewForeground.setOnLongClickListener(this)

        // Restore any change of phase because user is having a play.
        if(savedInstanceState != null)
            displayPhase = savedInstanceState.getSerializable(DISPLAY_PHASE) as Phases

        this.setupSharedPreferences()
        this.updatePhase()
    }

    /**
     * Override to create menu [menu] for screen, returning
     * true to display menu.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Override to handle selection of menu item [item], returning
     * true if menu selection was handled, otherwise false.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        } else if (id == R.id.action_acknowledgments) {
            val intent = Intent(this, AcknowledgmentsActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Apply preferences.
     */
    private fun setupSharedPreferences() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        // Display background moon.
        val displayBackgroundMoon = sharedPrefs.getBoolean(getString(R.string.background_moon_key), resources.getBoolean(R.bool.background_moon_default))
        if(!displayBackgroundMoon)
            imageViewBackground.setImageResource(0) // From answer to "How to clear an ImageView in Android?" by Mario Lenci: https://stackoverflow.com/questions/2859212/how-to-clear-an-imageview-in-android.

        // Display phase description.
        val displayPhaseDescription = sharedPrefs.getBoolean(getString(R.string.phase_description_key), resources.getBoolean(R.bool.phase_description_default))
        if(!displayPhaseDescription)
            textViewPhaseDescription.visibility = GONE

        // Sky.
        val sky = Skies.fromPrefs(this, sharedPrefs.getString(getString(R.string.sky_key), getString(R.string.sky_value_default)))
        constraintLayout.setBackgroundResource(sky.drawable)

        // Invert.
        val invert = sharedPrefs.getBoolean(getString(R.string.invert_key), resources.getBoolean(R.bool.invert_default))
        if(invert) {
            imageViewForeground.rotation = 180.0f
            imageViewBackground.rotation = 180.0f
        }

        sharedPrefs.registerOnSharedPreferenceChangeListener(this)
    }

    /**
     * Implement to ensure changed shared preferences [sharedPrefs], identified by
     * key [key] are immediately applied to MainActivity.
     */
    override fun onSharedPreferenceChanged(sharedPrefs: SharedPreferences?, key: String?) {
        if (key == getString(R.string.background_moon_key)) {
            val displayBackgroundMoon = sharedPrefs!!.getBoolean(getString(R.string.background_moon_key), resources.getBoolean(R.bool.background_moon_default))
            val drawable = if(displayBackgroundMoon) R.drawable.background_moon else 0
            imageViewBackground.setImageResource(drawable)
        }
        else if (key == getString(R.string.phase_description_key)) {
            val displayPhaseDescription = sharedPrefs!!.getBoolean(getString(R.string.phase_description_key), resources.getBoolean(R.bool.phase_description_default))
            val visibility = if(displayPhaseDescription) VISIBLE else GONE
            textViewPhaseDescription.visibility = visibility
        }
        else if (key == getString(R.string.sky_key)) {
            val sky = Skies.fromPrefs(this, sharedPrefs!!.getString(key, getString(R.string.sky_value_default)))
            constraintLayout.setBackgroundResource(sky.drawable)
        }
        else if (key == getString(R.string.invert_key)) {
            val invert = sharedPrefs!!.getBoolean(getString(R.string.invert_key), resources.getBoolean(R.bool.invert_default))
            val rotation = if(invert) 180.0f else 0.0f
            imageViewForeground.rotation = rotation
            imageViewBackground.rotation = rotation
        }
    }

    /**
     * Override to add current phase to [outState].
     *
     * Although any change of phase is insignificant, and is likely because user is
     * having fun, change must be persisted across configuration changes.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState?.putSerializable(DISPLAY_PHASE, displayPhase)
    }

    /**
     * Override to unregister shared preferences listener.
     */
    override fun onDestroy() {
        super.onDestroy()

        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

    /**
     * Increment to next phase of moon (just for fun)
     * Clicked ImageView [i] is unused.
     */
    fun nextPhase(i: View) {
        if(!displayPhaseChanged && (displayPhase == currentPhase)) {
            displayPhaseChanged = true
            Toast.makeText(this, getString(R.string.reset_current_phase), Toast.LENGTH_LONG).show()
        }

        displayPhase = Phases.getNextPhase(displayPhase)
        this.updatePhase()
    }

    /**
     * Reset to current moon phase.
     * Clicked View [i] is unused.
     */
    override fun onLongClick(i: View): Boolean {
        if(displayPhase != currentPhase) {
            displayPhase = currentPhase
            this.updatePhase()
            return true
        } else {
            return false;
        }
    }

    /**
     * Increment appearance of sky.
     * Clicked ConstraintLayout [c] is unused.
     */
    fun nextSky(c: View) {
        // Get current sky from shared preferences.
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val currentSky = Skies.fromPrefs(this, sharedPrefs.getString(getString(R.string.sky_key), getString(R.string.sky_value_default)))

        // Increment to next sky.
        val nextSky = Skies.getNextSky(currentSky)

        // Save to shared preferences (allow listener to apply changes).
        sharedPrefs.edit().putString(getString(R.string.sky_key), Skies.toPrefs(this, nextSky)).commit()
    }

    /**
     * Update image displaying phase of moon and its description.
     */
    private fun updatePhase() {
        imageViewForeground.setImageResource(displayPhase.drawable)
        textViewPhaseDescription.text = this.getText(displayPhase.stringName)
    }
}
