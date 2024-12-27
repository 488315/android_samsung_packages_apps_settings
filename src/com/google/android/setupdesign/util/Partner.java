package com.google.android.setupdesign.util;

import android.content.res.Resources;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Partner {
    public static Partner partner = null;
    public static boolean searched = false;
    public final String packageName;
    public final Resources resources;

    public Partner(Resources resources, String str) {
        this.packageName = str;
        this.resources = resources;
    }

    public static synchronized void resetForTesting() {
        synchronized (Partner.class) {
            searched = false;
            partner = null;
        }
    }
}
