package com.android.settings.network;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class GlobalSettingsChangeListener extends ContentObserver
        implements LifecycleObserver, AutoCloseable {
    public final Context mContext;
    public final String mField;
    public Lifecycle mLifecycle;
    public final AtomicBoolean mListening;
    public final Uri mUri;

    public GlobalSettingsChangeListener(Context context, String str) {
        this(Looper.getMainLooper(), context, str);
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        monitorUri(false);
        Lifecycle lifecycle = this.mLifecycle;
        if (lifecycle != null) {
            lifecycle.removeObserver(this);
        }
        this.mLifecycle = null;
    }

    public final void monitorUri(boolean z) {
        if (this.mListening.compareAndSet(!z, z)) {
            if (z) {
                this.mContext.getContentResolver().registerContentObserver(this.mUri, false, this);
            } else {
                this.mContext.getContentResolver().unregisterContentObserver(this);
            }
        }
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        if (this.mListening.get()) {
            onChanged$1();
        }
    }

    public abstract void onChanged$1();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        close();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        monitorUri(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        monitorUri(false);
    }

    public GlobalSettingsChangeListener(Looper looper, Context context, String str) {
        super(new Handler(looper));
        this.mContext = context;
        this.mUri = Settings.Global.getUriFor(str);
        this.mListening = new AtomicBoolean(false);
        monitorUri(true);
    }
}
