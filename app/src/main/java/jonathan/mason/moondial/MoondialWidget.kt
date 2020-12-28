package jonathan.mason.moondial

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.view.View
import android.widget.RemoteViews

/**
 * Moondial Widget, based upon Android Studio template.
 */
class MoondialWidget : AppWidgetProvider() {
    companion object {
        /**
         * Update all instances of widget to reflect changed shared preferences, using supplied
         * Context [context] and AppWidgetManager [appWidgetManager].
         *
         * Based on answer to "Update Android Widget From Activity" by Atul O Holic:
         * https://stackoverflow.com/questions/4073907/update-android-widget-from-activity/4074665.
         */
        fun updateAllAppWidgets(context: Context, appWidgetManager: AppWidgetManager) {
            // Update all instances of widget.
            appWidgetManager.updateAppWidget(ComponentName(context, MoondialWidget::class.java), createRemoteViews(context))
        }

        /**
         * Create RemoteViews and setup according to shared preferences, using supplied
         * context [Context].
         */
        private fun createRemoteViews(context: Context): RemoteViews {
            // Get shared preferences.
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val displayPhaseDescription = sharedPrefs.getBoolean(context.getString(R.string.phase_description_key), context.resources.getBoolean(R.bool.phase_description_default))
            val invert = sharedPrefs.getBoolean(context.getString(R.string.invert_key), context.resources.getBoolean(R.bool.invert_default))

            // Now that we know whether to invert or not, create RemoteViews with normal
            // or inverted version of layout.
            val views = RemoteViews(context.packageName, if(invert) R.layout.moondial_widget_inverted else R.layout.moondial_widget)

            // Set visibility of view responsible for phase description according to shared preferences.
            views.setViewVisibility(R.id.textViewPhaseDescription, if(displayPhaseDescription) View.VISIBLE else View.GONE)

            // Setup RemoteView to launch MainActivity screen if widget is clicked.
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            views.setOnClickPendingIntent(R.id.imageViewForeground, pendingIntent)

            // Make widget display current phase.
            val currentPhase = Phases.calculateCurrentPhase()
            views.setTextViewText(R.id.textViewPhaseDescription, context.getString(currentPhase.stringName))
            views.setImageViewResource(R.id.imageViewForeground, currentPhase.drawable)

            return views
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, createRemoteViews(context))
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
