package com.samsung.android.settings.analyzestorage.presenter.utils;

import android.content.Context;
import android.content.res.Configuration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ConfigurationUtils {
    public static boolean isInRTLMode(Context context) {
        Configuration configuration;
        return (context == null
                        || context.getResources() == null
                        || (configuration = context.getResources().getConfiguration()) == null
                        || configuration.getLayoutDirection() != 1)
                ? false
                : true;
    }
}
