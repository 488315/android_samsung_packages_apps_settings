package com.android.settings.development.featureflags;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FeatureFlagPersistent {
    public static final HashSet PERSISTENT_FLAGS;

    static {
        HashSet hashSet = new HashSet();
        PERSISTENT_FLAGS = hashSet;
        hashSet.add("settings_bluetooth_hearing_aid");
    }

    public static HashSet<String> getAllPersistentFlags() {
        return PERSISTENT_FLAGS;
    }
}
