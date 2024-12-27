package com.google.android.gms.common.internal;

import android.net.Uri;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class zzt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Uri.parse("https://plus.google.com/")
                .buildUpon()
                .appendPath("circles")
                .appendPath("find")
                .build();
    }
}
