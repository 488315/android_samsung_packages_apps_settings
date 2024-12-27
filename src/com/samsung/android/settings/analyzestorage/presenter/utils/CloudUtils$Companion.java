package com.samsung.android.settings.analyzestorage.presenter.utils;

import android.content.Context;
import android.os.Bundle;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CloudUtils$Companion {
    public static boolean isSupportCloud(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean z = false;
        try {
            Bundle call =
                    context.getContentResolver()
                            .call("myfiles", "SUPPORT_CLOUD", (String) null, (Bundle) null);
            Intrinsics.checkNotNull(call);
            z = call.getBoolean("result");
            Log.d("CloudUtils", "isSupportCloud : " + z);
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            return z;
        }
    }
}
