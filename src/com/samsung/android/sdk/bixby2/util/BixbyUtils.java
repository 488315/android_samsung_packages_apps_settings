package com.samsung.android.sdk.bixby2.util;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BixbyUtils {
    public static BixbyContextInfo getBixbyContextInfo(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = bundle.getBundle("contextInfo");
        return (bundle2 == null || bundle2.isEmpty())
                ? new BixbyContextInfo()
                : new BixbyContextInfo(bundle2);
    }
}
