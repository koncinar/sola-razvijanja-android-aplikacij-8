package com.comtrade.widgetprimer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Created by slavkod on 14.5.2015.
 * Widget class
 */
public class AppWidget extends AppWidgetProvider {
    public static final String SENDING_NEW_TEXT = "com.comtrade.widgetprimer.SENDING_NEW_TEXT";
    public static final String NEW_TEXT = "newText";
    public static final String CURRENT_TEXT = "currentText";

    private static String text;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (SENDING_NEW_TEXT.equals(action)) {
            text = intent.getExtras().getString(NEW_TEXT);

            Log.i("AppWidget", "New Text " + text);

            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            int[] ids = gm.getAppWidgetIds(new ComponentName(context, AppWidget.class));
            this.onUpdate(context, gm, ids);
        } else {
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = buildRemoveViews(context,appWidgetId, text);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        Log.i("AppWidget", "Update");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

        RemoteViews views = buildRemoveViews(context, appWidgetId, text);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    public static RemoteViews buildRemoveViews(Context context, int appWidgetId, String textToSet) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        // Get the layout for the App Widget and attach an on-click listener to the button
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        text = textToSet;
        if (TextUtils.isEmpty(text)) {
            text = context.getString(R.string.widget_text);
        }
        views.setTextViewText(R.id.widgetText, text);

        // Create an Intent to launch MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(CURRENT_TEXT, text);
        intent.setAction(SENDING_NEW_TEXT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.widgetButton, pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int minWidth = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);  // Contains the lower bound on the current width, in dp units, of a widget instance.
            int minHeight = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);  //Contains the lower bound on the current height, in dp units, of a widget instance.
            int maxWidth = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);  //Contains the upper bound on the current width, in dp units, of a widget instance.
            int maxHeight = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);  //Contains the upper bound on the current width, in dp units, of a widget instance.

            Log.i("ConfigureActivity", String.format("Size %d, %d, %d, %d", minWidth, minHeight, maxWidth, maxHeight));

            // Get the layout for the App Widget and attach an on-click listener to the button
            views.setViewVisibility(R.id.widgetButton, (minWidth < 200) ? View.GONE : View.VISIBLE);
        }

        return views;
    }
}
