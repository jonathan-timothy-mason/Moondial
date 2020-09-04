package jonathan.mason.moondial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * Main screen of app, displaying moon and its phases.
 */
class MainActivity(var currentPhase: Phases = Phases.NewNoCrescent) : AppCompatActivity() {

    private lateinit var imageViewUpper: ImageView
    private lateinit var textViewPhaseCaption: TextView

    /**
     * Perform initialisation of activity.
     * @param savedInstanceState Saved state of activity; not used.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewUpper = this.findViewById(R.id.imageViewUpper)
        textViewPhaseCaption =  this.findViewById(R.id.textViewPhaseCaption)
        //imageView.rotation = 180.0f
    }

    fun next(button: View) {
        currentPhase = Phases.getNextPhase(currentPhase)
        imageViewUpper.setImageResource(currentPhase.drawable)
        textViewPhaseCaption.text = this.getText(currentPhase.string)
    }
}
