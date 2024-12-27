package com.android.settingslib.spa.framework.util;

import android.content.Intent;

import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SpaIntentKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        CollectionsKt__CollectionsKt.listOf(
                (Object[])
                        new String[] {"spaActivityDestination", "highlightEntry", "sessionSource"});
    }

    public static void appendSpaParams$default(Intent intent, String str, String str2, int i) {
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            str2 = null;
        }
        if (str != null) {
            intent.putExtra("spaActivityDestination", str);
        }
        if (str2 != null) {
            intent.putExtra("sessionSource", str2);
        }
    }
}
