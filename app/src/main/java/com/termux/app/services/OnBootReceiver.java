package com.termux.app.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.termux.app.MyHackApp;

public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MyHackApp.onBootDevice(context,intent);
    }
}
