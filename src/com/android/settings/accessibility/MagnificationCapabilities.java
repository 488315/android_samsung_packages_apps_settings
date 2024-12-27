package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import com.android.settings.R;

import com.google.common.primitives.Ints;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MagnificationCapabilities {
    public static int getCapabilities(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return Settings.Secure.getIntForUser(
                contentResolver,
                "accessibility_magnification_capability",
                1,
                contentResolver.getUserId());
    }

    public static String getSummary(Context context, int i) {
        context.getResources().getStringArray(R.array.magnification_mode_summaries);
        String[] stringArray =
                context.getResources().getStringArray(R.array.magnification_type_summaries);
        int indexOf =
                Ints.indexOf(
                        i, context.getResources().getIntArray(R.array.magnification_mode_values));
        if (indexOf == -1) {
            indexOf = 0;
        }
        return stringArray[indexOf];
    }
}
