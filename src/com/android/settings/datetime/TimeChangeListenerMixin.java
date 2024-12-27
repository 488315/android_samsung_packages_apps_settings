package com.android.settings.datetime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TimeChangeListenerMixin extends BroadcastReceiver
        implements LifecycleObserver, OnPause, OnResume {
    public final UpdateTimeAndDateCallback mCallback;
    public final Context mContext;

    public TimeChangeListenerMixin(
            Context context, UpdateTimeAndDateCallback updateTimeAndDateCallback) {
        this.mContext = context;
        this.mCallback = updateTimeAndDateCallback;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        this.mContext.unregisterReceiver(this);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        UpdateTimeAndDateCallback updateTimeAndDateCallback = this.mCallback;
        if (updateTimeAndDateCallback != null) {
            ((DateTimeSettings) updateTimeAndDateCallback).updatePreferenceStates();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        this.mContext.registerReceiver(this, intentFilter, null, null);
    }
}
