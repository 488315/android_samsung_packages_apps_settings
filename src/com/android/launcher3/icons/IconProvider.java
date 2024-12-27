package com.android.launcher3.icons;

import android.content.res.Resources;

import androidx.core.os.BuildCompat;

import com.android.settingslib.applications.RecentAppOpsAccess;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class IconProvider {
    public static final int CONFIG_ICON_MASK_RES_ID =
            Resources.getSystem()
                    .getIdentifier(
                            "config_icon_mask",
                            "string",
                            RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);

    static {
        int i = BuildCompat.$r8$clinit;
    }
}
