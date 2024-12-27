package com.samsung.android.settings.asbase.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BudsPluginConstants {
    public static final Intent getBudsSoundEffectActivityIntent(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intent intent = new Intent("android.intent.action.MAIN");
        String string =
                Settings.System.getString(context.getContentResolver(), "buds_plugin_package_name");
        if (string == null) {
            string = ApnSettings.MVNO_NONE;
        }
        intent.setComponent(
                new ComponentName(
                        string,
                        "com.samsung.accessory.hearablemgr.module.soundeffect.SoundEffectActivity"));
        return intent;
    }

    public static final boolean isSupportBudsSettingJump(Context context) {
        Object createFailure;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (Settings.System.getInt(context.getContentResolver(), "buds_enable", 0) != 1) {
            return false;
        }
        try {
            createFailure =
                    context.getPackageManager()
                            .queryIntentActivities(getBudsSoundEffectActivityIntent(context), 0);
        } catch (Throwable th) {
            createFailure = ResultKt.createFailure(th);
        }
        if (createFailure instanceof Result.Failure) {
            createFailure = null;
        }
        List list = (List) createFailure;
        return list != null ? list.isEmpty() ^ true : false;
    }
}
