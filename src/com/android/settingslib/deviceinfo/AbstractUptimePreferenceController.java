package com.android.settingslib.deviceinfo;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.DateUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractUptimePreferenceController extends AbstractPreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    static final String KEY_UPTIME = "up_time";
    public MyHandler mHandler;
    public Preference mUptime;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MyHandler extends Handler {
        public WeakReference mStatus;

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            AbstractUptimePreferenceController abstractUptimePreferenceController =
                    (AbstractUptimePreferenceController) this.mStatus.get();
            if (abstractUptimePreferenceController == null) {
                return;
            }
            if (message.what == 500) {
                abstractUptimePreferenceController.mUptime.setSummary(
                        DateUtils.formatElapsedTime(SystemClock.elapsedRealtime() / 1000));
                sendEmptyMessageDelayed(500, 1000L);
            } else {
                throw new IllegalStateException("Unknown message " + message.what);
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(KEY_UPTIME);
        this.mUptime = findPreference;
        findPreference.setSummary(
                DateUtils.formatElapsedTime(SystemClock.elapsedRealtime() / 1000));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_UPTIME;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        if (this.mHandler == null) {
            MyHandler myHandler = new MyHandler();
            myHandler.mStatus = new WeakReference(this);
            this.mHandler = myHandler;
        }
        this.mHandler.sendEmptyMessage(500);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        if (this.mHandler == null) {
            MyHandler myHandler = new MyHandler();
            myHandler.mStatus = new WeakReference(this);
            this.mHandler = myHandler;
        }
        this.mHandler.removeMessages(500);
    }
}
