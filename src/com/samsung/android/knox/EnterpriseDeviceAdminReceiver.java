package com.samsung.android.knox;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EnterpriseDeviceAdminReceiver extends DeviceAdminReceiver {
    public ComponentName mWho;

    @Override // android.app.admin.DeviceAdminReceiver
    public ComponentName getWho(Context context) {
        ComponentName componentName = this.mWho;
        if (componentName != null) {
            return componentName;
        }
        ComponentName componentName2 = new ComponentName(context, getClass());
        this.mWho = componentName2;
        return componentName2;
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return null;
    }

    @Override // android.app.admin.DeviceAdminReceiver, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.app.action.ACTION_PASSWORD_CHANGED".equals(action)) {
            onPasswordChanged(context, intent);
            return;
        }
        if ("android.app.action.ACTION_PASSWORD_FAILED".equals(action)) {
            onPasswordFailed(context, intent);
            return;
        }
        if ("android.app.action.ACTION_PASSWORD_SUCCEEDED".equals(action)) {
            onPasswordSucceeded(context, intent);
            return;
        }
        if ("android.app.action.DEVICE_ADMIN_ENABLED".equals(action)) {
            onEnabled(context, intent);
            return;
        }
        if (!"android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED".equals(action)) {
            if ("android.app.action.DEVICE_ADMIN_DISABLED".equals(action)) {
                onDisabled(context, intent);
            }
        } else {
            CharSequence onDisableRequested = onDisableRequested(context, intent);
            if (onDisableRequested != null) {
                getResultExtras(true)
                        .putCharSequence("android.app.extra.DISABLE_WARNING", onDisableRequested);
            }
        }
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public void onDisabled(Context context, Intent intent) {}

    @Override // android.app.admin.DeviceAdminReceiver
    public void onEnabled(Context context, Intent intent) {}

    @Override // android.app.admin.DeviceAdminReceiver
    public void onPasswordChanged(Context context, Intent intent) {}

    @Override // android.app.admin.DeviceAdminReceiver
    public void onPasswordFailed(Context context, Intent intent) {}

    @Override // android.app.admin.DeviceAdminReceiver
    public void onPasswordSucceeded(Context context, Intent intent) {}

    public void onRecoveryPasswordRequested(Context context, Intent intent) {}
}
