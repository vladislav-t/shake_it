package com.siddharthks.sampleapp;

import android.app.Application;

import com.ook.group.android.otkritkionline.bugreport.Buglife;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: Replace `name@example.com` with your email to receive bug reports :)
        Buglife.initWithEmail(this, "name@example.com");
    }
}
