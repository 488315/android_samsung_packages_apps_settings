package com.samsung.android.settings.analyzestorage.presenter.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class StorageVolumeUtils {
    public static boolean isSdCardRestrictedByPolicy(Context context) {
        boolean z = false;
        if (context != null) {
            try {
                Cursor query =
                        ContentResolverWrapper.query(
                                context,
                                Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3"),
                                null,
                                "isSdCardEnabled",
                                null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            if ("false"
                                    .equals(
                                            query.getString(
                                                    query.getColumnIndexOrThrow(
                                                            "isSdCardEnabled")))) {
                                z = true;
                            }
                        }
                    } finally {
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e) {
                Log.e(
                        "StorageVolumeUtils",
                        "isSdCardRestricted() ] Exception e : " + e.getMessage());
            }
        }
        Log.d("StorageVolumeUtils", "isSdCardRestricted() ] isCheckWrite: false ret : " + z);
        return z;
    }
}
