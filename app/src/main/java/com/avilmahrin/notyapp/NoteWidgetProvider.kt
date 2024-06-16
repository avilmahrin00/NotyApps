package com.avilmahrin.notyapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.avilmahrin.notyapp.ui.mainActivity.MainActivity // Sesuaikan package ini jika berbeda

class NoteWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Set up the intent that starts the NoteWidgetService, which will
            // provide the views for this collection.
            val intent = Intent(context, NoteWidgetService::class.java)
            views.setRemoteAdapter(R.id.widget_list_view, intent)

            // Set up the empty view
            views.setEmptyView(R.id.widget_list_view, R.id.widget_empty_view)

            // Create an Intent to launch MainActivity
            val appIntent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
