package com.android.settingslib.applications;

import android.content.res.Configuration;
import android.content.res.Resources;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InterestingConfigChanges {
    public static String mCachedAppIconPkg;
    public int mLastDensity;
    public final Configuration mLastConfiguration = new Configuration();
    public final int mFlags = -2147470844;

    public final boolean applyNewConfig(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        Configuration configuration2 = this.mLastConfiguration;
        return (this.mFlags
                        & configuration2.updateFrom(
                                Configuration.generateDelta(configuration2, configuration)))
                != 0;
    }
}
