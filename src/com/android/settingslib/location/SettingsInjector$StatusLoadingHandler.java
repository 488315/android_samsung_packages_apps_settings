package com.android.settingslib.location;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Log;

import com.android.settings.location.AppSettingsInjector;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsInjector$StatusLoadingHandler extends Handler {
    public WeakReference mAllSettings;
    public Set mSettingsBeingLoaded;
    public Deque mSettingsToLoad;

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (Log.isLoggable("SettingsInjector", 3)) {
            Log.d("SettingsInjector", "handleMessage start: " + message + ", " + this);
        }
        int i = message.what;
        if (i == 1) {
            Set set = (Set) this.mAllSettings.get();
            if (set != null) {
                ((ArrayDeque) this.mSettingsToLoad).clear();
                ((ArrayDeque) this.mSettingsToLoad).addAll(set);
            }
        } else if (i == 2) {
            SettingsInjector$Setting settingsInjector$Setting =
                    (SettingsInjector$Setting) message.obj;
            settingsInjector$Setting.getClass();
            if (Log.isLoggable("SettingsInjector", 3)
                    && settingsInjector$Setting.startMillis != 0) {
                Log.d(
                        "SettingsInjector",
                        settingsInjector$Setting
                                + " update took "
                                + (SystemClock.elapsedRealtime()
                                        - settingsInjector$Setting.startMillis)
                                + " millis");
            }
            ((ArraySet) this.mSettingsBeingLoaded).remove(settingsInjector$Setting);
            removeMessages(3, settingsInjector$Setting);
        } else if (i != 3) {
            Log.wtf("SettingsInjector", "Unexpected what: " + message);
        } else {
            SettingsInjector$Setting settingsInjector$Setting2 =
                    (SettingsInjector$Setting) message.obj;
            ((ArraySet) this.mSettingsBeingLoaded).remove(settingsInjector$Setting2);
            if (Log.isLoggable("SettingsInjector", 5)) {
                StringBuilder sb = new StringBuilder("Timed out after ");
                settingsInjector$Setting2.getClass();
                sb.append(SystemClock.elapsedRealtime() - settingsInjector$Setting2.startMillis);
                sb.append(" millis trying to get status for: ");
                sb.append(settingsInjector$Setting2);
                Log.w("SettingsInjector", sb.toString());
            }
        }
        if (((ArraySet) this.mSettingsBeingLoaded).size() > 0) {
            if (Log.isLoggable("SettingsInjector", 2)) {
                Log.v(
                        "SettingsInjector",
                        "too many services already live for " + message + ", " + this);
                return;
            }
            return;
        }
        if (((ArrayDeque) this.mSettingsToLoad).isEmpty()) {
            if (Log.isLoggable("SettingsInjector", 2)) {
                Log.v("SettingsInjector", "nothing left to do for " + message + ", " + this);
                return;
            }
            return;
        }
        SettingsInjector$Setting settingsInjector$Setting3 =
                (SettingsInjector$Setting) ((ArrayDeque) this.mSettingsToLoad).removeFirst();
        AppSettingsInjector appSettingsInjector = settingsInjector$Setting3.this$0;
        ActivityManager activityManager =
                (ActivityManager) appSettingsInjector.mContext.getSystemService("activity");
        InjectedSetting injectedSetting = settingsInjector$Setting3.setting;
        if (activityManager.isUserRunning(injectedSetting.mUserHandle.getIdentifier())) {
            SettingsInjector$MessengerHandler settingsInjector$MessengerHandler =
                    new SettingsInjector$MessengerHandler();
            settingsInjector$MessengerHandler.mSettingRef =
                    new WeakReference(settingsInjector$Setting3);
            settingsInjector$MessengerHandler.mHandler = appSettingsInjector.mHandler;
            Messenger messenger = new Messenger(settingsInjector$MessengerHandler);
            Intent intent = new Intent();
            intent.setClassName(injectedSetting.packageName, injectedSetting.className);
            intent.putExtra("messenger", messenger);
            if (Log.isLoggable("SettingsInjector", 3)) {
                Log.d(
                        "SettingsInjector",
                        injectedSetting
                                + ": sending update intent: "
                                + intent
                                + ", handler: "
                                + settingsInjector$MessengerHandler);
                settingsInjector$Setting3.startMillis = SystemClock.elapsedRealtime();
            } else {
                settingsInjector$Setting3.startMillis = 0L;
            }
            appSettingsInjector.mContext.startServiceAsUser(intent, injectedSetting.mUserHandle);
        } else if (Log.isLoggable("SettingsInjector", 2)) {
            Log.v(
                    "SettingsInjector",
                    "Cannot start service as user "
                            + injectedSetting.mUserHandle.getIdentifier()
                            + " is not running");
        }
        ((ArraySet) this.mSettingsBeingLoaded).add(settingsInjector$Setting3);
        sendMessageDelayed(obtainMessage(3, settingsInjector$Setting3), 1000L);
        if (Log.isLoggable("SettingsInjector", 3)) {
            Log.d(
                    "SettingsInjector",
                    "handleMessage end "
                            + message
                            + ", "
                            + this
                            + ", started loading "
                            + settingsInjector$Setting3);
        }
    }

    @Override // android.os.Handler
    public final String toString() {
        return "StatusLoadingHandler{mSettingsToLoad="
                + this.mSettingsToLoad
                + ", mSettingsBeingLoaded="
                + this.mSettingsBeingLoaded
                + '}';
    }
}
