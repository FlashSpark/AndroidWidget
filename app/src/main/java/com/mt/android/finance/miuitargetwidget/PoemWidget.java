package com.mt.android.finance.miuitargetwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * Implementation of App Widget functionality.
 */
public class PoemWidget extends AppWidgetProvider {

    private static String TAG = "PoemWidget";
    private static final String ACTION_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    private static final String ACTION_CARD_REVERSE = "com.xytech.android.widget.click";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.i("", "appWidgetId=" + appWidgetId);

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.poem_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        // 根据配置更新展示
        // 设置点击响应
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.poem_widget);
        remoteViews.setOnClickPendingIntent(R.id.btn_change, createIntent(context, R.id.btn_change));

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

    private static PendingIntent createIntent(Context context, int resId) {
        Intent intent = new Intent();
        intent.setClass(context, PoemWidget.class);
        intent.setAction(context.getString(R.string.action_change));
        intent.setData(Uri.parse("id:" + resId));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case ACTION_CARD_REVERSE:

                break;
            case ACTION_UPDATE:

                break;
            default:
                break;
        }
        if (intent.getAction().equals(context.getString(R.string.action_change))) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.poem_widget);
            Uri data = intent.getData();
            int resId = -1;
            if (data != null) {
                resId = Integer.parseInt(data.getSchemeSpecificPart());
            }
            switch (resId) {
                case R.id.btn_change:
                    remoteViews.setViewVisibility(R.id.panel_day, TextView.GONE);
                    remoteViews.setViewVisibility(R.id.panel_night, TextView.GONE);
                    break;
            }
            //获得appwidget管理实例，用于管理appwidget以便进行更新操作
            AppWidgetManager manger = AppWidgetManager.getInstance(context);
            // 相当于获得所有本程序创建的appwidget
            ComponentName thisName = new ComponentName(context, PoemWidget.class);
            //更新widget
            manger.updateAppWidget(thisName, remoteViews);
        }
    }

    /**
     * 旋转动画的实现
     *
     * @param context
     * @param srcBitmap
     * @param degree
     * @return
     */
    private Bitmap rotateBitmap(Context context, Bitmap srcBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
        return tmpBitmap;
    }
}

