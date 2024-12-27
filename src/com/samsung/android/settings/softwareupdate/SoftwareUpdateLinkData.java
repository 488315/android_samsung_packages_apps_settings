package com.samsung.android.settings.softwareupdate;

import android.content.Context;
import android.content.Intent;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SoftwareUpdateLinkData {
    public final Intent intent;
    public final boolean packageEnabled;
    public final String topLevelKey;

    public SoftwareUpdateLinkData(Context context) {
        Intent intent;
        boolean z;
        String str;
        try {
            SoftwareUpdateVariant softwareUpdateVariant = SoftwareUpdateVariant.get(context);
            intent = softwareUpdateVariant.intent();
            str = softwareUpdateVariant.topLevelKey();
            z = true;
        } catch (RuntimeException e) {
            SemLog.w("SoftwareUpdateLinkData", "failed to initialize", e);
            intent = null;
            z = false;
            str = null;
        }
        this.packageEnabled = z;
        this.intent = intent;
        this.topLevelKey = str;
    }
}
