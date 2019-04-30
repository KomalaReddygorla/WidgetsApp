package com.example.harsha1123.widgetsapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    public static final String SHARED_PREFERENCES_FILE="com.example.harsha1123.widgetsapp";
    public static final String COUNT="count";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId)
    {
        //step2
        SharedPreferences preferences=context.getSharedPreferences(SHARED_PREFERENCES_FILE,Context.MODE_PRIVATE);
        int count=preferences.getInt(COUNT+appWidgetId,0);
        count++;
        String date= DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
        //step2 ends
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        //step4
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_update);
        views.setTextViewText(R.id.id,String.valueOf(appWidgetId));
        views.setTextViewText(R.id.updated_date,
                context.getResources().getString(R.string.date_format,count,date));
        //step4 ends

        //step5
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(COUNT+appWidgetId,count);
        editor.apply();
        Intent updateIntent=new Intent(context,MainActivity.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] array=new int[]{appWidgetId};
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,array);
        PendingIntent pi=PendingIntent.getBroadcast(
                context,appWidgetId,updateIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button_id,pi);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

