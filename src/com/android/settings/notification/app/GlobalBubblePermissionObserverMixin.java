package com.android.settings.notification.app;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GlobalBubblePermissionObserverMixin extends ContentObserver {
    public final Context mContext;
    public final Listener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {}

    public GlobalBubblePermissionObserverMixin(Context context, Listener listener) {
        super(new Handler(Looper.getMainLooper()));
        this.mContext = context;
        this.mListener = listener;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        Listener listener = this.mListener;
        if (listener != null) {
            ((AppBubbleNotificationSettings) listener).updatePreferenceStates();
        }
    }
}
