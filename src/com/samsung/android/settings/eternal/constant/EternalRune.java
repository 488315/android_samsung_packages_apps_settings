package com.samsung.android.settings.eternal.constant;

import android.os.Build;
import android.os.Debug;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EternalRune {
    public static final boolean SUPPORT_BACKUP_DATA_SIZE_VALIDATION = Debug.semIsProductDev();

    public static void isTestMode() {
        Build.TYPE.equals("user");
    }
}
