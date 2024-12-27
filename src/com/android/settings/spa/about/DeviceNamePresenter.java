package com.android.settings.spa.about;

import android.content.Context;

import com.android.settings.deviceinfo.DeviceNamePreferenceController;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeviceNamePresenter {
    public final DeviceNamePreferenceController deviceNamePreferenceController;

    public DeviceNamePresenter(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.deviceNamePreferenceController =
                new DeviceNamePreferenceController(context, "unused_key");
    }
}
