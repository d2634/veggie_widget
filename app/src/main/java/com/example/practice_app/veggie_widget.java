package com.example.practice_app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class veggie_widget extends AppWidgetProvider {
    private static final String ACTION_BUTTON1 = "com.example.practice_app.BUTTON1";
    private static int m_goal=4;
    private static int div=m_goal/3;
    private static int m_num=0;
    private static int temp=div;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.veggie_widget);
        views.setTextViewText(R.id.appwidget_text, "채식 시작!");

        views.setImageViewResource(R.id.imageView, R.drawable.tree1);
        m_num=0;
        temp=div;

        Intent intent = new Intent(context, veggie_widget.class).setAction(ACTION_BUTTON1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent, 0);
        views.setOnClickPendingIntent(R.id.button1, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.veggie_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), veggie_widget.class.getName());
        int[] appWidgets = appWidgetManager.getAppWidgetIds(thisAppWidget);

        final String action = intent.getAction();
        Log.e("click",action);


        if(action.equals(ACTION_BUTTON1)) {
            //Toast.makeText(context, "버튼을 클릭했어요.", Toast.LENGTH_LONG).show();
            veggie_widget.m_num=m_num+1;

            if(m_num<=temp||m_num>=m_goal){
                views.setTextViewText(R.id.appwidget_text, Integer.toString(m_num)+"번");
            }
            else{
                temp=temp+div;
                if(temp==div*2){
                    views.setTextViewText(R.id.appwidget_text, Integer.toString(m_num)+"번");
                    views.setImageViewResource(R.id.imageView, R.drawable.tree2);
                }
                else if(temp==div*3){
                    views.setTextViewText(R.id.appwidget_text, Integer.toString(m_num)+"번");
                    views.setImageViewResource(R.id.imageView, R.drawable.tree3);
                }
            }
            Log.e("message","done2!");
            AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, veggie_widget.class), views);
        }
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

