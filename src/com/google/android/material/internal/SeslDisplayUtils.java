package com.google.android.material.internal;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SeslDisplayUtils {
    public static int getPinnedEdgeWidth(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "pinned_edge_width");
        } catch (Settings.SettingNotFoundException e) {
            Log.w("SeslDisplayUtils", "Failed get EdgeWidth " + e.toString());
            return 0;
        }
    }

    public static boolean isPinEdgeEnabled(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "panel_mode", 0) == 1;
        } catch (Exception e) {
            Log.w("SeslDisplayUtils", "Failed get panel mode " + e.toString());
            return false;
        }
    }
}
