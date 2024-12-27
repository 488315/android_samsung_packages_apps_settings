package com.android.settings.slices;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class VolumeSliceHelper {
    static IntentFilter sIntentFilter;
    static Map<Uri, Integer> sRegisteredUri = new ConcurrentHashMap();

    public static void handleStreamChanged(Context context, int i) {
        for (Map.Entry<Uri, Integer> entry : sRegisteredUri.entrySet()) {
            if (entry.getValue().intValue() == i) {
                context.getContentResolver().notifyChange(entry.getKey(), null);
                if (i != 2) {
                    return;
                }
            }
        }
    }
}
