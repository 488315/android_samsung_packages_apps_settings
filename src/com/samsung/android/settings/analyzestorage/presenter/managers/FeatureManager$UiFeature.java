package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FeatureManager$UiFeature {
    public static final boolean isDefaultTheme(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return TextUtils.isEmpty(
                Settings.System.getString(
                        context.getContentResolver(), "current_sec_active_themepackage"));
    }
}
