package jonathan.mason.moondial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Todo:
 * Toggle background moon.
 * Settings.
 * Toggle hemisphere.
 */

/**
 * Main screen of app, displaying moon and its phases.
 * Current phase of the moon resides in [currentPhase].
 * Current appearance of sky resides in [currentSky]
 */
class MainActivity(var currentPhase: Phases = Phases.NewMoon, var currentSky: Skies = Skies.TwighlightDark) : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var imageViewUpper: ImageView
    private lateinit var textViewPhaseCaption: TextView

    /**
     * Perform initialisation of activity.
     * Saved state of activity [savedInstanceState] is unused.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        constraintLayout = this.findViewById(R.id.constraintLayout)
        imageViewUpper = this.findViewById(R.id.imageViewUpper)
        textViewPhaseCaption =  this.findViewById(R.id.textViewPhaseCaption)
        //imageView.rotation = 180.0f

        this.currentPhase = Phases.calculateCurrentPhase()

        this.updateUserInterface()
    }

    /**
     * Increment to next phase of moon.
     * Clicked ImageView [i] is unused.
     */
    fun nextPhase(i: View) {
        currentPhase = Phases.getNextPhase(currentPhase)
        this.updateUserInterface()
    }

    /**
     * Increment to next phase of moon.
     * Clicked ConstraintLayout [c] is unused.
     */
    fun nextSky(c: View) {
        currentSky = Skies.getNextSky(currentSky)
        this.updateUserInterface()
    }

    /**
     * Updated user interface to reflect corresponding member variable,
     * i.e. sky, moon phase and caption.
     */
    fun updateUserInterface() {
        constraintLayout.setBackgroundResource(currentSky.drawable)
        imageViewUpper.setImageResource(currentPhase.drawable)
        textViewPhaseCaption.text = this.getText(currentPhase.stringName)
    }
}
