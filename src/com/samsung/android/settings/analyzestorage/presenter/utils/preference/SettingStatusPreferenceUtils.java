package com.samsung.android.settings.analyzestorage.presenter.utils.preference;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SettingStatusPreferenceUtils {
    public static final void setInternalUnusedApplicationInfo(
            Context context, int i, String unusedAppSize) {
        Intrinsics.checkNotNullParameter(unusedAppSize, "unusedAppSize");
        Bundle bundle = new Bundle();
        bundle.putString("unused_app_size", unusedAppSize);
        bundle.putInt("unused_app_count", i);
        Bundle bundle2 = new Bundle();
        bundle2.putBundle("key_unused_app", bundle);
        if (context != null) {
            try {
                ContentResolver contentResolver = context.getContentResolver();
                if (contentResolver != null) {
                    contentResolver.call(
                            Uri.parse("content://myfiles/"),
                            "SET_APP_STATUS_LOG",
                            (String) null,
                            bundle2);
                }
            } catch (IllegalArgumentException e) {
                Log.e(
                        "SettingStatusPreferenceUtils",
                        "setInternalUnusedApplicationInfo() ] " + e.getMessage());
            }
        }
    }
}
