package com.example.sam.mebake.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.sam.mebake.Model.Ingredients;
import com.example.sam.mebake.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidgetProvider extends AppWidgetProvider {

    //string names to pick up data from json
    private static final String PREF_NAME = "prefname";
    private static final String RECIPES_NAME_PREF ="recipename";
    private String recipeName;



    public void onReceive(Context context, Intent intent) {
        //receive the update from intent
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), NewAppWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them (if one ID, or IDs)
        for(int appWidgetId : appWidgetIds){
            //load the updateAppWidget below
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        Intent intent = new Intent(context, NewAppRemoteViewsService.class);
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //extracting the string from Json because remotefactory wouldn't allow.
        String json1 = preferences.getString(RECIPES_NAME_PREF, null);
            Type type1 = new TypeToken<String>() {
            }.getType();
            Gson gson = new Gson();
            recipeName = gson.fromJson(json1,type1);


                views.setTextViewText(R.id.title_text, recipeName);
                views.setRemoteAdapter(R.id.widget_listview, intent);

                views.setEmptyView(R.id.widget_listview, R.id.empty_view);

                //tells widget manager to update
                appWidgetManager.updateAppWidget(appWidgetId, views);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.title_text);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview);



    }






    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void toBroadCast(Context context){
        //This will send a broadcast to the widget and change the data
        AppWidgetManager.getInstance(context);
        Intent updateIntent = new Intent();
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        //updateIntent.putExtra(NewAppWidgetProvider.WIDGET_IDS_KEY, appWidgetId);
        //updateIntent.putExtra(NewAppWidgetProvider.WIDGET_DATA_KEY, recipeName);
        context.sendBroadcast(updateIntent);
    }


}



//todo send the receive the intent and then somebody should send the intent(example like in our recyclerview)