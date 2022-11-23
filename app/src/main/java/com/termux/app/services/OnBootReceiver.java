package com.termux.app.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.termux.app.TermuxActivity;

public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // http://developer.alexanderklimov.ru/android/theory/boot.php
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent serviceLauncher = new Intent(context, TermuxActivity.class);
            serviceLauncher.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(serviceLauncher);
            Toast.makeText(context, "Start termux", Toast.LENGTH_SHORT).show();
        }
    }
}
