package com.android.settingslib.spaprivileged.settingsprovider;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;

import com.android.settingslib.spaprivileged.database.ContentChangeFlowKt;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsGlobalChangeFlowKt {
    public static final Flow settingsGlobalChangeFlow(Context context, String name, boolean z) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        Uri uriFor = Settings.Global.getUriFor(name);
        Intrinsics.checkNotNullExpressionValue(uriFor, "getUriFor(...)");
        return ContentChangeFlowKt.contentChangeFlow(context, uriFor, z);
    }
}
