package com.android.settings.fuelgauge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryBroadcastReceiver extends BroadcastReceiver {
    int mBatteryHealth;
    String mBatteryLevel;
    public OnBatteryChangedListener mBatteryListener;
    String mBatteryStatus;
    int mChargingStatus;
    public final Context mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnBatteryChangedListener {
        void onBatteryChanged(int i);
    }

    public BatteryBroadcastReceiver(Context context) {
        this.mContext = context;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        updateBatteryStatus(intent, false);
    }

    public final void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("battery.dock.defender.bypass");
        intentFilter.addAction("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED");
        updateBatteryStatus(this.mContext.registerReceiver(this, intentFilter, 2), true);
    }

    public final void updateBatteryStatus(Intent intent, boolean z) {
        if (intent == null || this.mBatteryListener == null) {
            return;
        }
        String action = intent.getAction();
        Log.d("BatteryBroadcastRcvr", "updateBatteryStatus: action=" + action);
        if (!"android.intent.action.BATTERY_CHANGED".equals(action)) {
            if ("android.os.action.POWER_SAVE_MODE_CHANGED".equals(action)) {
                this.mBatteryListener.onBatteryChanged(2);
                return;
            } else {
                if ("battery.dock.defender.bypass".equals(action)
                        || "android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED"
                                .equals(action)) {
                    this.mBatteryListener.onBatteryChanged(3);
                    return;
                }
                return;
            }
        }
        StringBuilder sb = Utils.sBuilder;
        String formatPercentage =
                com.android.settingslib.Utils.formatPercentage(
                        com.android.settingslib.Utils.getBatteryLevel(intent));
        String batteryStatus =
                com.android.settingslib.Utils.getBatteryStatus(this.mContext, intent, false);
        int intExtra = intent.getIntExtra("android.os.extra.CHARGING_STATUS", 1);
        int intExtra2 = intent.getIntExtra("health", 1);
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "Battery changed: level: ",
                        formatPercentage,
                        "| status: ",
                        batteryStatus,
                        "| chargingStatus: ");
        m.append(intExtra);
        m.append("| health: ");
        m.append(intExtra2);
        Log.d("BatteryBroadcastRcvr", m.toString());
        if (!intent.getBooleanExtra("present", true)) {
            Log.w("BatteryBroadcastRcvr", "Problem reading the battery meter.");
            this.mBatteryListener.onBatteryChanged(6);
        } else if (z) {
            this.mBatteryListener.onBatteryChanged(0);
        } else if (intExtra != this.mChargingStatus) {
            this.mBatteryListener.onBatteryChanged(5);
        } else if (intExtra2 != this.mBatteryHealth) {
            this.mBatteryListener.onBatteryChanged(4);
        } else if (!formatPercentage.equals(this.mBatteryLevel)) {
            this.mBatteryListener.onBatteryChanged(1);
        } else if (!batteryStatus.equals(this.mBatteryStatus)) {
            this.mBatteryListener.onBatteryChanged(3);
        }
        this.mBatteryLevel = formatPercentage;
        this.mBatteryStatus = batteryStatus;
        this.mChargingStatus = intExtra;
        this.mBatteryHealth = intExtra2;
    }
}
