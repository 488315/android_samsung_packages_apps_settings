package com.android.settings.display.darkmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DarkModeObserver {
    public Runnable mCallback;
    public final Context mContext;
    public final AnonymousClass1 mBatterySaverReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.display.darkmode.DarkModeObserver.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    DarkModeObserver.this.mCallback.run();
                }
            };
    public ContentObserver mContentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.android.settings.display.darkmode.DarkModeObserver.2
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    Runnable runnable;
                    super.onChange(z, uri);
                    if ((uri == null ? null : uri.getLastPathSegment()) == null
                            || (runnable = DarkModeObserver.this.mCallback) == null) {
                        return;
                    }
                    runnable.run();
                }
            };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.display.darkmode.DarkModeObserver$1] */
    public DarkModeObserver(Context context) {
        this.mContext = context;
    }

    @VisibleForTesting
    public ContentObserver getContentObserver() {
        return this.mContentObserver;
    }

    @VisibleForTesting
    public void setContentObserver(ContentObserver contentObserver) {
        this.mContentObserver = contentObserver;
    }

    public final void subscribe(Runnable runnable) {
        runnable.run();
        this.mCallback = runnable;
        Uri uriFor = Settings.Secure.getUriFor("ui_night_mode");
        Uri uriFor2 = Settings.Secure.getUriFor("ui_night_mode_custom_type");
        Uri uriFor3 = Settings.Secure.getUriFor("dark_theme_custom_start_time");
        Uri uriFor4 = Settings.Secure.getUriFor("dark_theme_custom_end_time");
        this.mContext
                .getContentResolver()
                .registerContentObserver(uriFor, false, this.mContentObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(uriFor2, false, this.mContentObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(uriFor3, false, this.mContentObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(uriFor4, false, this.mContentObserver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        this.mContext.registerReceiver(this.mBatterySaverReceiver, intentFilter);
    }

    public final void unsubscribe() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        try {
            this.mContext.unregisterReceiver(this.mBatterySaverReceiver);
        } catch (IllegalArgumentException e) {
            Log.w("DarkModeObserver", e.getMessage());
        }
        this.mCallback = null;
    }
}
