package com.comtrade.widgetprimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(sendText);
        text = (EditText) findViewById(R.id.text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String textOnWidget = extras.getString(AppWidget.CURRENT_TEXT);
            if (!TextUtils.isEmpty(textOnWidget)) {
                text.setText(textOnWidget);
            }
        }

    }

    private View.OnClickListener sendText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String textToSend = text.getText().toString();
            if (!TextUtils.isEmpty(textToSend)) {
                Intent intent = new Intent(MainActivity.this, AppWidget.class);
                intent.putExtra(AppWidget.NEW_TEXT, textToSend);
                intent.setAction(AppWidget.SENDING_NEW_TEXT);
                sendBroadcast(intent);

                finish();
            }
        }
    };
}

// VIRI:
// http://www.tutorialspoint.com/android/android_widgets.htm
// http://www.vogella.com/tutorials/AndroidWidgets/article.html
// https://developer.android.com/guide/topics/appwidgets/index.html
// https://developer.android.com/guide/practices/ui_guidelines/widget_design.html#anatomy_determining_size
// http://stackoverflow.com/questions/14270138/dynamically-adjusting-widgets-content-and-layout-to-the-size-the-user-defined-t