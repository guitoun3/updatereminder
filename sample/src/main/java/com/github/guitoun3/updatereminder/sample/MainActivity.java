package com.github.guitoun3.updatereminder.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.guitoun3.updatereminder.UpdateReminder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new UpdateReminder.Builder(this)
                .setBaseUrl("https://raw.githubusercontent.com/guitoun3/updatereminder/master/sample/server/")
                .setPath("version.json")
                .build()
                .checkUpdate();
    }


}
