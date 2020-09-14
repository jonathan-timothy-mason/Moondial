package jonathan.mason.moondial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Todo:
 * Icon.
 * Widget.
 * Default enums, etc.
 */

/**
 * Main screen of app, displaying moon and its phases.
 * Current phase of the moon resides in [currentPhase].
 */
class MainActivity(var currentPhase: Phases = Phases.calculateCurrentPhase()) : AppCompatActivity() {

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
            //val intent = Intent(this, AcknowledgmentsActivity::class.java)
            //startActivity(intent)
            //return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Apply preferences.
     */
    private fun setupSharedPreferences() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        // Invert.
        val invert = sharedPrefs.getBoolean(getString(R.string.invert_key), resources.getBoolean(R.bool.invert_default))
        if(invert) {
            imageViewForeground.rotation = 180.0f
            imageViewBackground.rotation = 180.0f
        }

        // Display background moon.
        val displayBackgroundMoon = sharedPrefs.getBoolean(getString(R.string.background_moon_key), resources.getBoolean(R.bool.background_moon_default))
        if(!displayBackgroundMoon)
            imageViewBackground.setImageResource(0) // From answer to "How to clear an ImageView in Android?" by Mario Lenci: https://stackoverflow.com/questions/2859212/how-to-clear-an-imageview-in-android.

        // Sky.
        val sky = Skies.fromPrefs(this, sharedPrefs.getString(getString(R.string.sky_key), getString(R.string.sky_value_default)))
        constraintLayout.setBackgroundResource(sky.drawable)
    }

    /**
     * Increment to next phase of moon.
     * Clicked ImageView [i] is unused.
     */
    fun nextPhase(i: View) {
        currentPhase = Phases.getNextPhase(currentPhase)
        this.updatePhase()
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

        // Save to shared preferences.
        sharedPrefs.edit().putString(getString(R.string.sky_key), Skies.toPrefs(this, nextSky)).commit()

        // Display.
        constraintLayout.setBackgroundResource(nextSky.drawable)
    }

    /**
     * Update image displaying phase of moon and its description.
     */
    private fun updatePhase() {
        //imageViewForeground.setImageResource(if(currentPhase != Phases.NewMoon) currentPhase.drawable else 0)
        imageViewForeground.setImageResource(currentPhase.drawable)
        textViewPhaseDescription.text = this.getText(currentPhase.stringName)
    }
}
