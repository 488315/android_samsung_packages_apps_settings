package com.samsung.android.settings.softwareupdate;

import android.content.Context;
import android.content.Intent;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SoftwareUpdateLaunchFromExternal {
    public final Intent intent;

    public SoftwareUpdateLaunchFromExternal(Context context) {
        Intent intent;
        try {
            SoftwareUpdateVariant softwareUpdateVariant = SoftwareUpdateVariant.get(context);
            intent =
                    softwareUpdateVariant
                            .intent()
                            .putExtra(
                                    "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY",
                                    softwareUpdateVariant.topLevelKey())
                            .addFlags(268468224);
        } catch (RuntimeException e) {
            SemLog.w("SoftwareUpdateLaunchFromExternal", "failed to initialize", e);
            intent = null;
        }
        this.intent = intent;
    }
}
