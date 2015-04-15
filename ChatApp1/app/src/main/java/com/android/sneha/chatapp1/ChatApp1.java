package com.android.sneha.chatapp1;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by sneha on 9/4/15.
 */
public class ChatApp1 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "YGMLd2APV7tCt4YjAnwQts7nKIqL1JKYu4Q49Bbv", "WJAAkd6AwcKxS6HDPUojkgDu6LPvO5GL4C9Z5Ni2");
        PushService.setDefaultPushCallback(getApplicationContext(), ChatActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();


    }
}
