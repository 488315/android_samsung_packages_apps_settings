package com.google.android.setupcompat.util;

import android.os.Build;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BuildCompatUtils {
    public static boolean isAtLeastU() {
        String str = Build.VERSION.CODENAME;
        return str.equals("REL") || (!str.equals("REL") && str.compareTo("UpsideDownCake") >= 0);
    }
}
