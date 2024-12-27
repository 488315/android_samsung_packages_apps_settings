package com.android.settings.overlay;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FeatureFactory$Companion {
    public static Context getAppContext() {
        Context context = FeatureFactoryImpl._appContext;
        if (context != null) {
            return context;
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }
}
