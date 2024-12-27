package com.android.settings.network;

import android.content.Context;

import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalChangeFlowKt;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MobileDataEnabledFlowKt {
    public static final Flow mobileDataEnabledFlow(Context context, int i, boolean z) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Flow flow = SettingsGlobalChangeFlowKt.settingsGlobalChangeFlow(context, "mobile_data", z);
        if (i == -1) {
            return flow;
        }
        return FlowKt.merge(
                flow,
                SettingsGlobalChangeFlowKt.settingsGlobalChangeFlow(
                        context, "mobile_data" + i, false));
    }
}
