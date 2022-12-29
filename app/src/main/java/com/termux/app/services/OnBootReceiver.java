package com.termux.app.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.termux.app.MyHackApp;
import com.termux.app.TermuxActivity;

public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MyHackApp.onBootDevice(context,intent);
    }
}
