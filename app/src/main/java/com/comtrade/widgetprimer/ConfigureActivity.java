package com.comtrade.widgetprimer;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;

/**
 * Created by slavkod on 15.5.2015.
 * Configure Activity for the widget
 */
public class ConfigureActivity extends Activity {

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);



        text = (EditText) findViewById(R.id.text);
        findViewById(R.id.cancel).setOnClickListener(onCancel);
        findViewById(R.id.add).setOnClickListener(onAdd);

    }

    private View.OnClickListener onCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener onAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle extras = getIntent().getExtras();
            String textToSet = text.getText().toString();

            if ((extras != null) && !TextUtils.isEmpty(textToSet)) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ConfigureActivity.this);

                // get the App Widget ID from the Intent that launched the Activity
                int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

                // Update the Widget with values
                RemoteViews views = AppWidget.buildRemoveViews(ConfigureActivity.this, appWidgetId, textToSet);

                // Tell the AppWidgetManager to perform an update on the current app widget
                appWidgetManager.updateAppWidget(appWidgetId, views);


                // Create the return Intent, set it with the Activity result, and finish the Activity
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        }
    };
}
