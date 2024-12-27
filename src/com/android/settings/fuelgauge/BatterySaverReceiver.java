package com.android.settings.fuelgauge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatterySaverReceiver extends BroadcastReceiver {
    public BatterySaverListener mBatterySaverListener;
    public final Context mContext;
    public boolean mRegistered;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface BatterySaverListener {
        void onBatteryChanged(boolean z);

        void onPowerSaveModeChanged();
    }

    public BatterySaverReceiver(Context context) {
        this.mContext = context;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.os.action.POWER_SAVE_MODE_CHANGED".equals(action)) {
            BatterySaverListener batterySaverListener = this.mBatterySaverListener;
            if (batterySaverListener != null) {
                batterySaverListener.onPowerSaveModeChanged();
                return;
            }
            return;
        }
        if (!"android.intent.action.BATTERY_CHANGED".equals(action)
                || this.mBatterySaverListener == null) {
            return;
        }
        this.mBatterySaverListener.onBatteryChanged(intent.getIntExtra("plugged", 0) != 0);
    }

    public final void setListening(boolean z) {
        if (z && !this.mRegistered) {
            this.mContext.registerReceiver(
                    this,
                    ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                            "android.os.action.POWER_SAVE_MODE_CHANGED",
                            "android.intent.action.BATTERY_CHANGED"));
            this.mRegistered = true;
        } else {
            if (z || !this.mRegistered) {
                return;
            }
            this.mContext.unregisterReceiver(this);
            this.mRegistered = false;
        }
    }
}
