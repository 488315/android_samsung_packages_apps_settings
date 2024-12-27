package com.samsung.android.settings.fuelgauge;

import android.content.Context;
import android.net.Uri;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DeviceCareManager {
    public static final Uri AUTHORITY_FAS_URI;
    public final Context mContext;

    static {
        Uri parse = Uri.parse("content://com.samsung.android.sm");
        Uri.withAppendedPath(parse, "ForcedAppStandby");
        Uri.withAppendedPath(parse, "VerifyForcedAppStandby");
        AUTHORITY_FAS_URI = Uri.parse("content://com.samsung.android.sm.battery.bridge");
    }

    public DeviceCareManager(Context context) {
        this.mContext = context;
    }
}
