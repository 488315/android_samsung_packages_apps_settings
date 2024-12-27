package com.android.settings.development.autofill;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutofillDeveloperSettingsObserver extends ContentObserver {
    public final Runnable mChangeCallback;
    public final ContentResolver mResolver;

    public AutofillDeveloperSettingsObserver(Context context, Runnable runnable) {
        super(new Handler(Looper.getMainLooper()));
        this.mResolver = context.getContentResolver();
        this.mChangeCallback = runnable;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri, int i) {
        this.mChangeCallback.run();
    }

    public final void register() {
        this.mResolver.registerContentObserver(
                Settings.Global.getUriFor("autofill_logging_level"), false, this, -1);
        this.mResolver.registerContentObserver(
                Settings.Global.getUriFor("autofill_max_partitions_size"), false, this, -1);
        this.mResolver.registerContentObserver(
                Settings.Global.getUriFor("autofill_max_visible_datasets"), false, this, -1);
    }
}
