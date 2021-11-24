package com.example.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Class for providing the Widget for the Reminder App.
 * This widget has a TextView and a Button.
 * The Button triggers an Intent with an extra and calls a specific task in the MainActivity.
 *
 * Layout File: layout_widget.xml
 */
public class ReminderAppWidgetProvider extends AppWidgetProvider {

    // Extra for the Intent
    public static final String EXTRA_BUTTON = "EXTRA_BUTTON";

    /**
     * The onUpdate method is being triggered at the creation and the update of a Widget.
     * RemoteViews inhabits the Layout for the widget.
     * A new Intent and PendingIntent leads to the MainActivity.
     * @param context is the environment that the Widget lives in.
     * @param appWidgetManager is information about the Widgets.
     * @param appWidgetIds are the IDs of all created Widgets.
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // The loop goes trough all Widget IDs
        for (int appWidgetId : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);

            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(EXTRA_BUTTON, true);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }
}
