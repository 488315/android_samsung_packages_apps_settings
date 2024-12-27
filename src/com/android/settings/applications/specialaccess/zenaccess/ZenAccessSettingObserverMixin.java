package com.android.settings.applications.specialaccess.zenaccess;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenAccessSettingObserverMixin extends ContentObserver
        implements LifecycleObserver, OnStart, OnStop {
    public final Context mContext;
    public final Listener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {
        void onZenAccessPolicyChanged();
    }

    public ZenAccessSettingObserverMixin(Context context, Listener listener) {
        super(new Handler(Looper.getMainLooper()));
        this.mContext = context;
        this.mListener = listener;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onZenAccessPolicyChanged();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("enabled_notification_listeners"), false, this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this);
    }
}
