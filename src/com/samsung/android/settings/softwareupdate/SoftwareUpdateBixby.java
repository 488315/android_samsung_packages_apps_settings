package com.samsung.android.settings.softwareupdate;

import android.content.Context;
import android.content.Intent;

import com.samsung.android.util.SemLog;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SoftwareUpdateBixby {
    public final Intent intent;
    public final boolean packageEnabled;

    public SoftwareUpdateBixby(Context context) {
        Intent intent;
        boolean z;
        try {
            intent = new SoftwareUpdateLaunchFromExternal(context).intent;
            Objects.requireNonNull(intent);
            Intent intent2 = intent;
            z = true;
        } catch (RuntimeException e) {
            SemLog.w("SoftwareUpdateBixby", "failed to initialize", e);
            intent = null;
            z = false;
        }
        this.intent = intent;
        this.packageEnabled = z;
    }
}
