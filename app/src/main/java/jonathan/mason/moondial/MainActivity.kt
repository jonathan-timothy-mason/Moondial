package jonathan.mason.moondial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

/**
 * Todo:
 * Toggle background moon.
 * Settings.
 * Toggle hemisphere.
 */

/**
 * Main screen of app, displaying moon and its phases.
 * @param currentPhase Current phase of the moon.
 * @param currentSky Current appearance of sky.
 */
class MainActivity(var currentPhase: Phases = Phases.NewNoCrescent, var currentSky: Skies = Skies.TwighlightDark) : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var imageViewUpper: ImageView
    private lateinit var textViewPhaseCaption: TextView

    /**
     * Perform initialisation of activity.
     * @param savedInstanceState Saved state of activity; not used.
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
     * @param i Clicked ImageView; unused.
     */
    fun nextPhase(i: View) {
        currentPhase = Phases.getNextPhase(currentPhase)
        this.updateUserInterface()
    }

    /**
     * Increment to next phase of moon.
     * @param c Clicked ConstraintLayout; unused.
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
        textViewPhaseCaption.text = this.getText(currentPhase.string)
    }
}
